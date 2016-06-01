//
//  SplashPageVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 06/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import CoreData

class LoginVC: UIViewController, UITextFieldDelegate, NSFetchedResultsControllerDelegate {
    
    @IBOutlet var loadingView:UIView!
    @IBOutlet var activity:UIActivityIndicatorView!
    @IBOutlet var loadingTxt:UILabel!
    
    @IBOutlet var username:UITextField!
    @IBOutlet var password:UITextField!
    @IBOutlet var loginBtn:UIButton!
    @IBOutlet var rememberMe:UISwitch!
    
    @IBOutlet var registerBtn:UIButton!
    @IBOutlet var registerLbl:UILabel!
    
    override func viewWillAppear(animated: Bool) {
        let appDel:AppDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        let ctx:NSManagedObjectContext = appDel.managedObjectContext!
        
        let request = NSFetchRequest(entityName: "User")
        var error:NSError?
        
        if let results = ctx.executeFetchRequest(request, error: &error) as? [User] {
            if results.count > 0 {
                let user = results[0]
                adManager.user = user
            
                if serverAPI.isConnectedToNetwork() {
                    hideViews()
                    newsParser.getNewsFeed(activity, update: loadingTxt, app: self)
                } else {
                    dispatch_async(dispatch_get_main_queue(), {
                        let newsVC = self.storyboard?.instantiateViewControllerWithIdentifier("NewsFeedVC") as! UITabBarController
                        self.presentViewController(newsVC, animated: true, completion: nil)
                    })
                }
            }
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        if adManager.user != nil {
            username.text = adManager.user.username
            password.text = adManager.user.password
        }
    }
    
    @IBAction func login(sender: UIButton){
        if adManager.user == nil {
            let remember = rememberMe.on
            println("Remember: \(remember)")
            var task = loginHandler.login(username.text, password: password.text,  remember:remember, app: self, completion: {
                (code) in
                switch code {
                case 0:
                    var alert = UIAlertView(title: "Unable to login", message: "Invalid username and password. Try again.", delegate: self, cancelButtonTitle: "Dismiss")
                    
                    self.resetView()
                    
                    alert.show()
                case 1:
                    self.activity.startAnimating()
                    newsParser.getNewsFeed(self.activity, update:self.loadingTxt, app: self)
                case 2:
                    var alert = UIAlertView(title: "Incomplete form", message: "Please enter a valid username and password.", delegate: self, cancelButtonTitle: "Dismiss")
                    alert.show()
                case 404:
                    var alert = UIAlertView(title: "Unable to connect to the internet", message: "Please ensure you have an active connection (Mobile Data/Wifi).", delegate: self, cancelButtonTitle: "Dismiss")
                    
                    self.resetView()
                    
                    alert.show()
                default:
                    break;
                }
            })
        }
    }
    
    @IBAction func showRegistrationForm(sender: UIButton){
        let vc = storyboard?.instantiateViewControllerWithIdentifier("RegisterVC") as! UINavigationController
        
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    func resetView(){
        self.loadingTxt.hidden = true
        self.loadingView.hidden = true
        self.activity.hidden = true
        self.loginBtn.setTitle("Sign in", forState: .Normal)
        self.loginBtn.hidden = false
        self.registerBtn.hidden = false
        self.registerLbl.hidden = false
    }
    
    func hideViews(){
        self.loadingTxt.hidden = false
        self.loadingView.hidden = false
        self.activity.hidden = false
        self.loginBtn.setTitle("Signing in...", forState: .Normal)
        self.loginBtn.hidden = true
        self.registerBtn.hidden = true
        self.registerLbl.hidden = true
    }

    func textFieldShouldBeginEditing(textField: UITextField) -> Bool {
        if textField.tag == 0 {
            //self.view.frame = CGRect(x: view.frame.minX, y: view.frame.minY - 30, width: view.frame.width, height: view.frame.height)
            textField.background = UIImage(named: "username_selected")
        } else if textField.tag == 1 {
            self.view.frame = CGRect(x: view.frame.minX, y: view.frame.minY - 100, width: view.frame.width, height: view.frame.height)
            textField.background = UIImage(named: "password_selected")
        }
        
        return true
    }
    
    func textFieldShouldEndEditing(textField: UITextField) -> Bool {
        if textField.tag == 0 {
            //self.view.frame = CGRect(x: view.frame.minX, y: view.frame.minY + 30, width: view.frame.width, height: view.frame.height)
            textField.background = (textField.text as NSString).length > 0 ? UIImage(named: "username_selected") : UIImage(named: "username_default")
        } else if textField.tag == 1 {
            self.view.frame = CGRect(x: view.frame.minX, y: view.frame.minY + 100, width: view.frame.width, height: view.frame.height)
            textField.background = (textField.text as NSString).length > 0 ? UIImage(named: "password_selected") : UIImage(named: "password_default")
        }
        return textField.resignFirstResponder()
    }
    
    override func touchesBegan(touches: Set<NSObject>, withEvent event: UIEvent) {
        username.resignFirstResponder()
        password.resignFirstResponder()
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        if textField.tag == 0 {
            username.resignFirstResponder()
        }
        return true
    }
}

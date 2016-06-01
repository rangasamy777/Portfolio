//
//  RegisterVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 16/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

class RegisterVC: UITableViewController, UITextFieldDelegate {
    
    @IBOutlet var username:UITextField!
    @IBOutlet var password:UITextField!
    @IBOutlet var confirmPass:UITextField!
    @IBOutlet var email:UITextField!
    @IBOutlet var validationImg:UIImageView!

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func tableView(tableView: UITableView, willSelectRowAtIndexPath indexPath: NSIndexPath) -> NSIndexPath? {
        return nil
    }

    @IBAction func closeView(sender: UIBarButtonItem){
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    @IBAction func signUp(sender: UIBarButtonItem){
        if formCompleted() {
            if isValidPass() && getLength(confirmPass.text) > 0 && getLength(password.text) > 0 {
                
                let loadingView = interfaces.showView(self.view, msg: "Registering")
                loadingView.hidden = false
                self.view.addSubview(loadingView)
                
                let registerEmail = email.text.stringByAddingPercentEncodingWithAllowedCharacters(.URLHostAllowedCharacterSet())
                registrationHandler.registerUser(username.text, password: password.text, email: registerEmail!, completion: {
                    (code) in
                    
                    switch code{
                    case 200:
                        loadingView.hidden = true
                        self.dismissViewControllerAnimated(true, completion: nil)
                    case 201:
                        loadingView.hidden = true
                        var alert = UIAlertView(title: "Username already taken", message: "This username has already been taken. Please choose another name.", delegate: self, cancelButtonTitle: "Dismiss")
                        alert.show()
                    default:
                        break
                    }
                    
                })
            } else {
                var alert = UIAlertView(title: "Validation error", message: "Please ensure your passwords are matching.", delegate: self, cancelButtonTitle: "Dismiss")
                alert.show()
            }
        } else {
            var alert = UIAlertView(title: "Incomplete form", message: "Please fill out all the relevant fields in the form correctly.", delegate: self, cancelButtonTitle: "Dismiss")
            alert.show()
        }
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
            if isValidPass() && getLength(confirmPass.text) > 0 && getLength(password.text) > 0 {
                validationImg.image = UIImage(named: "correct_icon")
                validationImg.hidden = false
            } else {
                validationImg.image = UIImage(named: "incorrect_icon")
                validationImg.hidden = false
            }
    }
    
    override func touchesBegan(touches: Set<NSObject>, withEvent event: UIEvent) {
        username.resignFirstResponder()
        password.resignFirstResponder()
        confirmPass.resignFirstResponder()
        email.resignFirstResponder()
    }
    
    func isValidPass() -> Bool {
        return password.text == confirmPass.text
    }
    
    func formCompleted() -> Bool {
        return getLength(username.text) > 0 && getLength(password.text) > 0 && getLength(confirmPass.text) > 0 && getLength(email.text) > 0
    }
    
    func getLength(string: String) -> Int {
        return (string as NSString).length
    }
}

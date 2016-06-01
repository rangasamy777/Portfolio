//
//  LoginHandler.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 14/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import CoreData

var loginHandler:LoginHandler = LoginHandler()

class LoginHandler {
    
    let queue:dispatch_queue_t = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)
    var registerUrl:String = "http://islam.envisiontechllc.org/php/register.php?"
    var loginUrl:String = "http://islam.envisiontechllc.org/php/login.php?"
    
    func login(username:String, password:String, remember:Bool, app:LoginVC, completion: (code:Int) -> Void) -> Int {
        var code:Int = 200
        
        if (username as NSString).length > 0 && (password as NSString).length > 0 {
            UIApplication.sharedApplication().networkActivityIndicatorVisible = true
            
            app.loadingTxt.text = "Logging in"
            app.loadingTxt.hidden = false
            app.loadingView.hidden = false
            app.activity.hidden = false
            app.registerLbl.hidden = true
            app.registerBtn.hidden = true
            app.loginBtn.hidden = true
            app.activity.startAnimating()
            
            dispatch_async(queue, {
                var url:NSURL = NSURL(string: self.loginUrl + "username=\(username)&password=\(password)")!
                
                let task = NSURLSession.sharedSession().dataTaskWithURL(url, completionHandler: {
                    (data, response, error) in
                    
                    var error:NSError?
                    if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                        
                        var responseCode:NSString = response.objectForKey("code") as! NSString
                        code = responseCode.integerValue
                        
                        completion(code: code)
                        
                        if code == 1 {
                            var details:NSDictionary = response.objectForKey("0") as! NSDictionary
                            let username = details.objectForKey("Username") as! String
                            let password = details.objectForKey("Password") as! String
                            let premium = details.objectForKey("Premium") as! NSString
                            let bool = premium.integerValue == 1 ? true : false
                            
                            let appDel:AppDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
                            let ctx:NSManagedObjectContext = appDel.managedObjectContext!
                            
                            if remember {
                                var ent = NSEntityDescription.insertNewObjectForEntityForName("User", inManagedObjectContext: ctx) as! User
                                
                                ent.username = username
                                ent.password = password
                                ent.setValue(bool, forKey: "premium")
                                
                                adManager.user = ent
                                
                                println("Saving user: \(ent)")
                                ctx.save(nil)
                            } else {
                                let request = NSFetchRequest(entityName: "User")
                                var err:NSError?
                                
                                let array:NSArray = ctx.executeFetchRequest(request, error: &err) as! NSArray
                                for user in array {
                                    ctx.deleteObject(user as! NSManagedObject)
                                    println("Deleting: \(user)")
                                }
                                
                                var ent = NSEntityDescription.entityForName("User", inManagedObjectContext: ctx)
                                let newUser = User(entity: ent!, insertIntoManagedObjectContext: ctx)
                                
                                newUser.username = username
                                newUser.setValue(bool, forKey: "premium")
                                adManager.user = newUser
                            }
                        }
                        
                    }
                })
                
                task.resume()
            })
        } else {
            code = 2
        }
        
        completion(code: code)
        
        return code
    }
}

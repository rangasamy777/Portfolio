//
//  RegistrationHandler.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 16/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var registrationHandler:RegistrationHandler = RegistrationHandler()

class RegistrationHandler {
    
    let path:String = "http://islam.envisiontechllc.org/php/register.php?"
    let queue:dispatch_queue_t = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)
    
    func registerUser(username:String, password:String, email:String, completion: (code: Int) -> Void ) {
        dispatch_async(queue, {
            let url = NSURL(string: self.path + "&username=\(username)&password=\(password)&email=\(email)")
            
            let task = NSURLSession.sharedSession().dataTaskWithURL(url!, completionHandler: {
                (data, response, errpr) in
                
                var error:NSError?
                if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                    
                    let code = response.objectForKey("code") as! NSString
                    completion(code: code.integerValue)
                }
                
            })
            task.resume()
        })
    }
   
}

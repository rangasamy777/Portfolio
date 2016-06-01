//
//  User.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 14/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import CoreData
import iAd

var adManager:PremiumManager = PremiumManager()

class PremiumManager {
    
    var user:User!
    var premiumUrl:String = "http://islam.envisiontechllc.org/php/registerPremium.php?username="
    let queue:dispatch_queue_t = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)
    
    func registerPremium(app:MoreVC, code:Int) {
        if code == 0 {
            if let url = NSURL(string: self.premiumUrl + adManager.user.username + "&check=0") {
                let task = NSURLSession.sharedSession().dataTaskWithURL(url, completionHandler: {
                    (data, response, error) in
                    
                    var error:NSError?
                    if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                        
                        let responseCode = response.objectForKey("code") as! NSString
                        if responseCode.integerValue == 200 {
                            println("Registered for premium")
                            var success = UIAlertView(title: "Successfully purchased", message: "You have successfully purchased removal of adverts. Please log out and restart the app for changes to take effect.", delegate: self, cancelButtonTitle: "Dismiss")
                            success.show()
                        }
                    }
                })
                task.resume()
            }
        } else {
            var alert = UIAlertView(title: "Already purchased", message: "Looks like you have already purchased the abilility to remove ads. Your purchase has been restored.", delegate: self, cancelButtonTitle: "Dismiss")
            alert.show()
        }
    }
    
    func checkPremium(completion: (code:Int) -> Void) {
        if !adManager.isPremium() {
            dispatch_async(queue, {
                if let url = NSURL(string: self.premiumUrl + adManager.user.username + "&check=1") {
                    let task = NSURLSession.sharedSession().dataTaskWithURL(url, completionHandler: {
                        (data, response, error) in
                        
                        var error:NSError?
                        if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                            
                            println(response)
                            
                            if let code = response.objectForKey("Premium") as? NSString {
                                println("Found code'")
                                completion(code: code.integerValue)
                            }
                        }
                    })
                    task.resume()
                }
            })
        } else {
            var alert = UIAlertView(title: "Already purchased", message: "Looks like you have already purchased the abilility to remove ads. Your purchase has been restored.", delegate: self, cancelButtonTitle: "Dismiss")
            alert.show()
        }
    }
    
    func handleAds(ad:ADBannerView) {
        if user != nil {
            if isPremium() {
                ad.hidden = true
            }
        }
    }
    
    func isPremium() -> Bool {
        return user.premium
    }
}


public class User: NSManagedObject {
   
    @NSManaged var username:String
    @NSManaged var password:String
    @NSManaged var premium:Bool
    
}

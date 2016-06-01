//
//  Profile.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 06/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var profileController:ProfileController = ProfileController()

class ProfileController {
    var selectedProfile:Profile!
}

class Profile {
   
    var username:String!
    var likes:String!
    var posts:String!
    var imgUrl:String!
    var bgUrl:String!
    var long:String!
    var lat:String!
    
    var bgImg:UIImage!
    var profileImg:UIImage!
    
    let queue:dispatch_queue_t = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)
    
    func initProfile(data:NSDictionary){
        self.username = data.objectForKey("Username") as! String
        self.likes = data.objectForKey("Likes") as! String
        self.posts = data.objectForKey("Posts") as! String
        self.imgUrl = data.objectForKey("ProfileImg") as! String
        self.bgUrl = data.objectForKey("BackgroundImg") as! String
        self.long = self.getLong(data)
        self.lat = self.getLat(data)
        self.bgImg = UIImage(data: NSData(contentsOfURL: NSURL(string: bgUrl)!)!)
        self.profileImg = UIImage(data: NSData(contentsOfURL: NSURL(string: imgUrl)!)!)
    }
    
    func getLong(data:NSDictionary) -> String {
        var res = data.objectForKey("Location") as! String
        var tempLong = res.componentsSeparatedByString(",")[0]
        
        return String(tempLong)
    }
    
    func getLat(data:NSDictionary) -> String {
        var res = data.objectForKey("Location") as! String
        var tempLong = res.componentsSeparatedByString(",")[1]
        
        return String(tempLong)
    }
}

//
//  ServerAPI.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 05/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var serverAPI:ServerAPI = ServerAPI()

class ServerAPI {
    
    let queue:dispatch_queue_t = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)
    let likeURL = "http://localhost/php/likePost.php?postID="
    let profileUrl = "http://localhost/php/getProfile.php?username="
    let postUrl = "http://localhost/php/getPosts.php?username="
    
    func likePost(news:News){
        dispatch_async(queue, {
            let url = NSURL(string: self.likeURL + news.postID)!
            
            let task = NSURLSession.sharedSession().dataTaskWithURL(url, completionHandler: {
                (data, response, error) in
                
                var error:NSError?
                if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                    
                    if error != nil {
                        println("Error: \(error?.localizedDescription)")
                    } else {
                        println("Liked successfully")
                    }
                }
            })
            task.resume()
        })
    }
    
    func getProfile(news:News, profileVC:ProfileVC, collection:UICollectionView){
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
        
        dispatch_async(queue, {
            let url = NSURL(string: self.profileUrl + news.author)!
            
            let task = NSURLSession.sharedSession().dataTaskWithURL(url, completionHandler: {
                (data, response, error) in
                
                var error:NSError?
                if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                    
                    if error != nil {
                        println("Erorr: \(error?.localizedDescription)")
                    } else {
                        let arr = response.objectForKey("profile") as! NSArray
                        
                        for profile in arr {
                            var user:Profile = Profile()
                            user.initProfile(profile as! NSDictionary)
                            profileController.selectedProfile = user
                            
                            //println("Profile: \(user.username)")
                            self.getPosts(user, collection: collection)
                        }
                        
                        dispatch_async(dispatch_get_main_queue(), {
                            profileVC.loadFields()
                            collection.reloadData()
                            UIApplication.sharedApplication().networkActivityIndicatorVisible = false
                        })
                    }
                }
            })
            task.resume()
        })
    }
    
    func getPosts(user:Profile, collection:UICollectionView){
        dispatch_async(queue, {
            let url = NSURL(string: self.postUrl + user.username)!
            
            let task = NSURLSession.sharedSession().dataTaskWithURL(url, completionHandler: {
                (data, response, error) in
                
                var error:NSError?
                if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                    
                    if error != nil {
                        println("Erorr: \(error?.localizedDescription)")
                    } else {
                        let arr = response.objectForKey("posts") as! NSArray
                        
                        for result in arr {
                            var post:News = News()
                            post.initWithData(result as! NSDictionary)
                            newsController.profilePosts.append(post)
                            
                            dispatch_async(dispatch_get_main_queue(), {
                                collection.reloadData()
                            })
                        }
                    }
                }
            })
            task.resume()
        })
    }
    
    func isConnectedToNetwork()->Bool{
        
        var Status:Bool = false
        let url = NSURL(string: "http://google.com/")
        let request = NSMutableURLRequest(URL: url!)
        request.HTTPMethod = "HEAD"
        request.cachePolicy = NSURLRequestCachePolicy.ReloadIgnoringLocalAndRemoteCacheData
        request.timeoutInterval = 10.0
        
        var response: NSURLResponse?
        
        var data = NSURLConnection.sendSynchronousRequest(request, returningResponse: &response, error: nil) as NSData?
        
        if let httpResponse = response as? NSHTTPURLResponse {
            if httpResponse.statusCode == 200 {
                Status = true
            }
        }
        
        return Status
    }
}

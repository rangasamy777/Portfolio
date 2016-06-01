//
//  NewsFeedParser.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 05/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var newsParser:NewsFeedParser = NewsFeedParser()

class NewsFeedParser {
   
    var url:String = "http://islam.envisiontechllc.org/php/getnews.php"
    let queue:dispatch_queue_t = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)
    
    func getNewsFeed(indicator:UIActivityIndicatorView, update:UILabel, app:LoginVC){
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
        newsController.entries.removeAll(keepCapacity: false)
        
        update.text = "Loading News Feed"
        app.activity.startAnimating()
        
        dispatch_async(queue, {
            let newsURL:NSURL = NSURL(string: self.url)!
            
            let task = NSURLSession.sharedSession().dataTaskWithURL(newsURL, completionHandler: {
                (data, response, error) in
                
                var error:NSError?
                if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                    
                    if error != nil {
                        println("Error: \(error)")
                    } else {
                        let arr = response.objectForKey("posts") as! NSArray
                        
                        for data in arr {
                            dispatch_async(dispatch_get_main_queue(), {
                                update.text = "Fetching Posts"
                            })
                            
                            var news:News = News()
                            news.initWithData(data as! NSDictionary)
                            
                            newsController.addPost(news)
                        }
                        dispatch_async(dispatch_get_main_queue(), {
                            UIApplication.sharedApplication().networkActivityIndicatorVisible = false
                            indicator.stopAnimating()
                            
                            let newsFeedVC = app.storyboard?.instantiateViewControllerWithIdentifier("NewsFeedVC") as! UITabBarController
                            
                            app.presentViewController(newsFeedVC, animated: true, completion: nil)
                        })
                    }
                    
                }
                
            })
            task.resume()
        })
    }
    
    func reloadNewsFeed(app:NewsFeedVC){
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
        newsController.entries.removeAll(keepCapacity: false)
        
        dispatch_async(queue, {
            let newsURL:NSURL = NSURL(string: self.url)!
            
            let task = NSURLSession.sharedSession().dataTaskWithURL(newsURL, completionHandler: {
                (data, response, error) in
                
                var error:NSError?
                if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                    
                    if error != nil {
                        println("Error: \(error)")
                    } else {
                        let arr = response.objectForKey("posts") as! NSArray
                        
                        for data in arr {
                            dispatch_async(dispatch_get_main_queue(), {
                                app.navigationItem.title = "Loading News Feed"
                            })
                            
                            var news:News = News()
                            news.initWithData(data as! NSDictionary)
                            
                            newsController.addPost(news)
                        }
                        dispatch_async(dispatch_get_main_queue(), {
                            UIApplication.sharedApplication().networkActivityIndicatorVisible = false
                            app.navigationItem.title = ""
                            app.collectionView?.reloadData()
                        })
                    }
                    
                }
                
            })
            task.resume()
        })
    }
}

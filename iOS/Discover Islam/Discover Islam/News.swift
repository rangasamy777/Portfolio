//
//  News.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 05/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var newsController:NewsController = NewsController()

class NewsController{
    var entries = [News]()
    var profilePosts = [News]()
    var selectedNews:News!
    
    func getCount() -> Int {
        return entries.count
    }
    
    func getPostForIndex(index:Int) -> News {
        return entries[index]
    }
    
    func addPost(news:News){
        entries.append(news)
    }
    
    func setSelected(news:News){
        selectedNews = news
    }
}

class News {
    
    var postID:String!
    var imgURL:String!
    var title:String!
    var desc:String!
    var author:String!
    var authorImg:String!
    
    var likes:String!
    var liked:Bool!
    
    var profileImg:UIImage!
    var bgImg:UIImage!
    
    func initWithData(data: NSDictionary){
        self.imgURL = data.objectForKey("Image") as! String
        self.title = data.objectForKey("Title") as! String
        self.desc = data.objectForKey("Description") as! String
        self.author = data.objectForKey("Author") as! String
        self.authorImg = data.objectForKey("AuthorImg") as! String
        self.likes = data.objectForKey("Likes") as! String
        self.liked = false
        self.postID = data.objectForKey("ID") as! String
        
        self.profileImg = UIImage(data: NSData(contentsOfURL: NSURL(string: authorImg)!)!)
        self.bgImg = UIImage(data: NSData(contentsOfURL: NSURL(string: imgURL)!)!)
    }
    
    func incrementLikes(){
        var number:Int = (likes as NSString).integerValue
        number++
        likes = String(number)
        liked = true
    }
   
}

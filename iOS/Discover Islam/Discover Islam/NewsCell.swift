//
//  NewsCell.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 05/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

class NewsCell: UICollectionViewCell {
    
    @IBOutlet var img:UIImageView!
    @IBOutlet var authorImg:UIImageView!
    @IBOutlet var author:UILabel!
    @IBOutlet var title:UILabel!
    @IBOutlet var desc:UILabel!
    @IBOutlet var likes:UILabel!
    @IBOutlet var likeImg:UIImageView!
    
    @IBOutlet var moreBtn:UIButton!
    var selectedIndex:Int!
    var viewController:UINavigationController!
    var collectionView:UICollectionView!
    
    func generateCell(viewController:UINavigationController, collectionView:UICollectionView, news:News, index:Int){
        self.img.image = news.bgImg
        self.generateProfileImage(news)
        self.author.text = news.author
        self.title.text = news.title
        self.desc.text = news.desc
        self.likes.text = news.likes
        self.viewController = viewController
        self.collectionView = collectionView
        self.selectedIndex = index
        self.loadGestures()
    }
    
    func generateProfileImage(news:News) {
        var image:UIImage = news.profileImg
        self.authorImg.image = image
        self.authorImg.layer.cornerRadius = self.authorImg.frame.size.width / 5
        self.authorImg.clipsToBounds = true
    }
    
    func loadGestures(){
        likeImg.alpha = 0.0
    }
    
    func showLiked(){
        let post = newsController.getPostForIndex(selectedIndex)
        
        if !post.liked {
            var anim:CABasicAnimation = CABasicAnimation(keyPath: "opacity")
            anim.duration = 0.5
            anim.autoreverses = true
            anim.fromValue = 0.0
            anim.toValue = 1.0
        
            post.incrementLikes()
            serverAPI.likePost(post)
            collectionView.reloadData()
            
            likeImg.layer.addAnimation(anim, forKey: "animateOpacity")
        }
    }
    
    @IBAction func openNewsDetails(sender: UIButton){
        let details = viewController.storyboard?.instantiateViewControllerWithIdentifier("NewsDetailsVC") as! NewsDetailsVC
        details.selectedNews = (sender.tag == 0 ? newsController.getPostForIndex(selectedIndex) : newsController.profilePosts[selectedIndex])
        newsController.selectedNews = newsController.getPostForIndex(selectedIndex)
        
        viewController.pushViewController(details, animated: true)
    }
}

//
//  NewsFeedVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 05/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import iAd

let reuseIdentifier = "NewsCell"

class NewsFeedVC: UICollectionViewController, ADBannerViewDelegate {
    
    var tapRecogniser:UITapGestureRecognizer!
    var timer:NSTimer!
    var count:Int = 0
    
    var adBanner:ADBannerView!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.loadBackground()
        self.timer = NSTimer.scheduledTimerWithTimeInterval(1.0, target: self, selector: Selector("reloadTable"), userInfo: nil, repeats: true)
        self.loadAd()
    }
    
    override func viewWillAppear(animated: Bool) {
        if adBanner != nil {
            adManager.handleAds(adBanner)
        }
    }
    
    override func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return newsController.getCount()
    }

    override func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! NewsCell
    
        let news:News = newsController.getPostForIndex(indexPath.row)
        cell.generateCell(navigationController!, collectionView: collectionView, news: news, index: indexPath.row)
    
        return cell
    }
    
    override func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        let newsPost = newsController.getPostForIndex(indexPath.row)
        let cell = collectionView.cellForItemAtIndexPath(indexPath) as! NewsCell
        
        self.addGestures(cell, news: newsPost)
    }
    
    func loadAd(){
        adBanner = ADBannerView()
        
        var height = self.view.frame.height - (tabBarController!.tabBar.frame.height * 2) - navigationController!.navigationBar.frame.height
        adBanner.frame = CGRectMake(0, height, 320, 50)
        adBanner.delegate = self
        self.view.addSubview(adBanner)
        
        collectionView?.frame = CGRectMake(0, 0, view.frame.width, self.view.frame.height - adBanner.frame.height)
    }
    
    func bannerView(banner: ADBannerView!, didFailToReceiveAdWithError error: NSError!) {
        
    }
    
    func loadBackground(){
        var purpleColor:UIColor = UIColor(red: 0.352, green: 0.337, blue: 0.839, alpha: 1.0)
        var greenColor:UIColor = UIColor(red: 0.18, green: 0.62, blue: 0.0, alpha: 1.0)
        
        tabBarController?.tabBar.tintColor = purpleColor
    }

    func addGestures(cell:NewsCell, news:News){
        if !news.liked {
            tapRecogniser = UITapGestureRecognizer()
            tapRecogniser.numberOfTapsRequired = 2
            tapRecogniser.numberOfTouchesRequired = 1
            tapRecogniser.addTarget(self, action: Selector(cell.showLiked()))
            
            cell.addGestureRecognizer(tapRecogniser)
        } else {
            cell.removeGestureRecognizer(tapRecogniser)
        }
    }
    
    func reloadTable(){
        if serverAPI.isConnectedToNetwork() {
            if count <= 5 {
                collectionView?.reloadData()
                count++
            } else {
                timer.invalidate()
            }
        } else {
            self.navigationItem.title = "Offline Mode"
            var alert = UIAlertView(title: "Unable to retreive news", message: "Please ensure you have an active internet connection. \n\nIf you have not allowed Discover Islam to use Mobile Data, ensure it is enabled in \"Settings\"=>\"Discover Islam\"", delegate: self, cancelButtonTitle: "Dismiss")
            alert.show()
            timer.invalidate()
        }
    }
    
    @IBAction func reloadCollectionView(sender: AnyObject){
        if serverAPI.isConnectedToNetwork() {
            newsParser.reloadNewsFeed(self)
        } else {
            self.navigationItem.title = "Offline Mode"
        }
    }
}

//
//  NewsDetailsVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 05/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import iAd

class NewsDetailsVC: UITableViewController, UITextViewDelegate, ADBannerViewDelegate {
    
    @IBOutlet var profileImg:UIImageView!
    @IBOutlet var bgImg:UIImageView!
    @IBOutlet var author:UIButton!
    @IBOutlet var newsTitle:UILabel!
    @IBOutlet var desc:UITextView!
    @IBOutlet var likes:UILabel!
    
    @IBOutlet var descIMG:UIImageView!
    
    
    var adBanner:ADBannerView!
    var selectedNews:News!

    override func viewDidLoad() {
        super.viewDidLoad()
        self.loadNewsTitle()
        self.loadBanner()
    }
    
    override func viewWillAppear(animated: Bool) {
        if adBanner != nil {
            adManager.handleAds(adBanner)
        }
    }
    
    override func tableView(tableView: UITableView, willSelectRowAtIndexPath indexPath: NSIndexPath) -> NSIndexPath? {
        return nil
    }
    
    func loadBanner(){
        adBanner = ADBannerView()
        adBanner.delegate = self
        
        var height = tableView.frame.height - (tabBarController!.tabBar.frame.height  * 2.5) - navigationController!.navigationBar.frame.height
        
        adBanner.frame = CGRectMake(0, height, 320, 50)
        
        self.view.addSubview(adBanner)
    }
    
    func bannerView(banner: ADBannerView!, didFailToReceiveAdWithError error: NSError!) {
        
    }
    
    func loadNewsTitle(){
        if selectedNews != nil {

            var image:UIImage = selectedNews.profileImg
            self.profileImg.image = image
            self.profileImg.layer.cornerRadius = self.profileImg.frame.size.width / 5
            self.profileImg.clipsToBounds = true
            self.profileImg.backgroundColor = UIColor.whiteColor()
            
            bgImg.image = selectedNews.bgImg
            author.setTitle(selectedNews.author, forState: UIControlState.Normal)
            newsTitle.text = selectedNews.title
            desc.text = selectedNews.desc
            likes.text = selectedNews.likes
        }
    }
    
    @IBAction func openProfile(sender:UIButton){
        let profileView = storyboard?.instantiateViewControllerWithIdentifier("ProfileVC") as! ProfileVC
        
        profileView.navigationItem.title = "Loading Data"
        
        self.navigationController?.pushViewController(profileView, animated: true)
    }
}

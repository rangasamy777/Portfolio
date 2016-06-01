//
//  NameVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 15/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import iAd

class NameVC: UIViewController, UITableViewDelegate, UITableViewDataSource, ADBannerViewDelegate {
    
    @IBOutlet var adBanner:ADBannerView!

    override func viewDidLoad() {
        super.viewDidLoad()
        nameParser.parseData()
    }
    
    override func viewWillAppear(animated: Bool) {
        if adBanner != nil {
            adManager.handleAds(adBanner)
        }
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return nameController.entries.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("NameCell") as! NameCell
        let name:NameOfAllah = nameController.entries[indexPath.row]
        
        cell.generateCell(name)
        
        return cell
    }
    
    func bannerView(banner: ADBannerView!, didFailToReceiveAdWithError error: NSError!) {
        
    }
}

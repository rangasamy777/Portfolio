//
//  ProfileVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 06/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import MapKit
import iAd

class ProfileVC: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, ADBannerViewDelegate {

    @IBOutlet var profileImg:UIImageView!
    @IBOutlet var bgImg:UIImageView!
    @IBOutlet var posts:UICollectionView!
    @IBOutlet var bannerView:ADBannerView!
    
    var user:Profile!
    
    override func viewDidLoad() {
        
    }
    
    override func viewWillAppear(animated: Bool) {
        serverAPI.getProfile(newsController.selectedNews, profileVC: self, collection:posts)
    }
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return newsController.profilePosts.count
    }
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! NewsCell
        
        let news:News = newsController.profilePosts[indexPath.row]
        cell.generateCell(navigationController!, collectionView: collectionView, news: news, index: indexPath.row)
        
        return cell
    }
    
    func loadFields(){
        user = profileController.selectedProfile
        bgImg.image = user.bgImg
        
        var image:UIImage = user.profileImg
        self.profileImg.image = image
        self.profileImg.layer.cornerRadius = self.profileImg.frame.size.width / 5
        self.profileImg.clipsToBounds = true
        
        self.navigationItem.title = user.username
    }
    
    @IBAction func openMapForPlace() {
        
        var lat1 : NSString = user.lat
        var lng1 : NSString = user.long
        
        var latitute:CLLocationDegrees =  lat1.doubleValue
        var longitute:CLLocationDegrees =  lng1.doubleValue
        
        let regionDistance:CLLocationDistance = 10000
        var coordinates = CLLocationCoordinate2DMake(latitute, longitute)
        let regionSpan = MKCoordinateRegionMakeWithDistance(coordinates, regionDistance, regionDistance)
        var options = [
            MKLaunchOptionsMapCenterKey: NSValue(MKCoordinate: regionSpan.center),
            MKLaunchOptionsMapSpanKey: NSValue(MKCoordinateSpan: regionSpan.span)
        ]
        var placemark = MKPlacemark(coordinate: coordinates, addressDictionary: nil)
        var mapItem = MKMapItem(placemark: placemark)
        mapItem.name = "\(user.username)"
        mapItem.openInMapsWithLaunchOptions(options)
        
    }
}

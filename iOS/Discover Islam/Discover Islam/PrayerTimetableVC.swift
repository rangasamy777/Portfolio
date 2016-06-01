//
//  PrayerTimetableVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 07/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import CoreData
import iAd

class PrayerTimetableVC: UIViewController, UITableViewDelegate, UITableViewDataSource, ADBannerViewDelegate {
    
    @IBOutlet var date:UILabel!
    @IBOutlet var date2:UILabel!
    @IBOutlet var table:UITableView!
    @IBOutlet var notifications:UIBarButtonItem!
    
    @IBOutlet var bannerView:ADBannerView!
    
    var timer:NSTimer!
    var prefs:NSUserDefaults!
    var shouldNotify = false

    override func viewDidLoad() {
        super.viewDidLoad()
        
        timetableParser.parseData()
        self.getDate()
        self.timer = NSTimer.scheduledTimerWithTimeInterval(60.0, target: self, selector: Selector("timerGetDate"), userInfo: nil, repeats: true)
        self.checkPrefs()
        
        self.loadNotificationSettings()
    }
    
    override func viewWillAppear(animated: Bool) {
        if bannerView != nil {
            adManager.handleAds(bannerView)
        }
    }

    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("PrayerCell") as! PrayerCell
        let prayer = prayerController.entries[indexPath.row]
        
        cell.generateCell(prayer)
        
        return cell
    }

    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return prayerController.entries.count
    }
    
    func tableView(tableView: UITableView, willSelectRowAtIndexPath indexPath: NSIndexPath) -> NSIndexPath? {
        return nil
    }
    
    @IBAction func clickedNavOptions(sender:AnyObject){
        switch sender.tag {
        case 0:
            table.reloadData()
        case 1:
            shouldNotify = !shouldNotify
            
            prefs = NSUserDefaults(suiteName: "group.org.envisiontechllc.discoverislam")
            prefs.setObject(shouldNotify, forKey: "notifications")
            prefs.synchronize()
            
            var msg:String = (shouldNotify ? "You have turned notifications on." : "You have turned notifications off.")
            var img:UIImage = (shouldNotify ? UIImage(named: "alarm_selected") : UIImage(named: "alarm_unselected"))!
            self.notifications.image = img
            self.setAlerts()
            
            var alert = UIAlertView(title: "Notification Options", message: msg, delegate: self, cancelButtonTitle: "Dismiss")
            alert.show()
        default:
            break;
        }
    }
    
    func timerGetDate(){
        date2.text = getDayString()
    }
    
    func getDate(){
        var dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = "dd/MM/yy"
        let dayOfWeek = getDayOfWeek(dateFormatter.stringFromDate(NSDate()))
        let dayName = getDayOfWeekName(dayOfWeek)
        
        date.text = "\(dayName)"
        date2.text = getDayString()
    }
    
    func getDayOfWeek(today:String)->Int {
        
        let formatter  = NSDateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        let todayDate = formatter.dateFromString(today)!
        let myCalendar = NSCalendar(calendarIdentifier: NSCalendarIdentifierGregorian)!
        let myComponents = myCalendar.components(.CalendarUnitWeekday, fromDate: todayDate)
        let weekDay = myComponents.weekday
        return weekDay
    }
    
    func getDayOfWeekName(day:Int) -> String {
        switch day {
        case 3:
            return "Monday"
        case 4:
            return "Tuesday"
        case 5:
            return "Wednesday"
        case 6:
            return "Thursday"
        case 7:
            return "Friday"
        case 1:
            return "Saturday"
        case 2:
            return "Sunday"
        default:
            return ""
        }
    }
    
    func getDayString() -> String {
        let timestamp = NSDateFormatter.localizedStringFromDate(NSDate(), dateStyle: .MediumStyle, timeStyle: .ShortStyle)
        return String(timestamp)
    }
    
    func checkPrefs(){
        prefs = NSUserDefaults(suiteName: "group.org.envisiontechllc.discoverislam")
        
        if prefs.objectForKey("notifications") != nil {
            let notify = prefs.objectForKey("notifications") as! Int
            
            if notify == 1 {
                notifications.image = UIImage(named: "alarm_selected")
                shouldNotify = true
                println("Notifications are on")
            } else {
                notifications.image = UIImage(named: "alarm_unselected")
                println("Notifications are off")
            }
        }
    }
    
    func setAlerts(){
        if shouldNotify {
            dispatch_async(newsParser.queue, {
                for prayer in prayerController.notifications {
                    var notification:UILocalNotification = UILocalNotification()
                    notification.category = "FIRST_CATEGORY"
                    notification.alertBody = "\(prayer.name) is calling!"
                    notification.fireDate = prayer.date
                    notification.alertAction = "Dismiss"
                    notification.soundName = "athaan.m4r"
                    
                    println("Notification date: \(notification.fireDate!)")
                    var dateNow = NSDate()
                    
                    //println("Date: \(dateNow)")
                    
                    UIApplication.sharedApplication().scheduleLocalNotification(notification)
                }
            })
        } else {
            var app:UIApplication = UIApplication.sharedApplication()
            if let arr:NSArray = app.scheduledLocalNotifications as? NSArray {
                println("Removing \(arr.count) notifications")
                for event in arr {
                    var notification = event as! UILocalNotification
                    app.cancelLocalNotification(notification)
                }
            }
        }
    }
    
    func loadNotificationSettings(){
        let types:UIUserNotificationType = UIUserNotificationType.Alert | UIUserNotificationType.Badge | UIUserNotificationType.Sound
        let settings:UIUserNotificationSettings = UIUserNotificationSettings(forTypes: types, categories: nil)
        
        UIApplication.sharedApplication().registerUserNotificationSettings(settings)
    }
    
    func loadIAdPreferences(){
        
    }
    
    func bannerView(banner: ADBannerView!, didFailToReceiveAdWithError error: NSError!) {
        
    }
}

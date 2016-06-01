//
//  Prayer.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 07/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var prayerController:PrayerController = PrayerController()

class PrayerController {
    var entries = [Prayer]()
    var notifications = [Prayer]()
    
    func sort(){
        entries.sort({
            return ($0.time as NSString).integerValue <= ($1.time as NSString).integerValue
        })
    }
}

class Prayer {
    
    var name:String!
    var time:String!
    var timeLeft:String!
    var date:NSDate!
    
    func generate(name:String, time:String, date:NSDate){
        self.name = name
        self.time = time
        self.timeLeft = self.getTimeLeft(time)
        self.date = date
    }
    
    func getTimeLeft(time:String) -> String {
        var dateComp:NSDateComponents = NSDateComponents()
        var calendar:NSCalendar = NSCalendar(calendarIdentifier: NSGregorianCalendar)!
        var dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = "MM"
        
        let timestamp = NSDateFormatter()
        timestamp.dateFormat = "dd"
        let dateNow = (timestamp.stringFromDate(NSDate()) as NSString).integerValue
        
        dateComp.year = 2015
        dateComp.month = (dateFormatter.stringFromDate(NSDate()) as NSString).integerValue
        dateComp.day = dateNow
        dateComp.hour = getHour(time).integerValue
        dateComp.minute = getMinutes(time).integerValue
        
        var date:NSDate = calendar.dateFromComponents(dateComp)!
        var nowDate:NSDate = NSDate()
        
        let distanceBetweenDates = nowDate.timeIntervalSinceDate(date)
        
        return secondsToHoursMinutesSeconds(Int(distanceBetweenDates))
    }
    
    func getHour(time:String) -> NSString {
        return NSString(string: time.componentsSeparatedByString(":")[0])
    }
    
    func getMinutes(time:String) -> NSString {
        return NSString(string: time.componentsSeparatedByString(":")[1])
    }
    
    func secondsToHoursMinutesSeconds (seconds : Int) -> String {
        let (hr,  minf) = modf (Double(seconds / 3600))
        let (min, secf) = modf (Double((seconds / 60) % 60))
        
        return String("\(Int(hr))h \(Int(min))m")
    }
}

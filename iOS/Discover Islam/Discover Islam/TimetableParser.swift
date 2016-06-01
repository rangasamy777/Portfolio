//
//  TimetableParser.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 06/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var timetableParser:TimetableParser = TimetableParser()

class TimetableParser {
    
    func parseData() {
        if let path = NSBundle.mainBundle().pathForResource("july", ofType: "json") {
            if let data = NSData(contentsOfMappedFile: path) {
                var error:NSError?
                
                if let json = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                    
                    if error != nil {
                        println("Error: \(error?.localizedDescription)")
                    } else {
                        let arr = json.objectForKey("prayers") as! NSArray
                        
                        let timestamp = NSDateFormatter()
                        timestamp.dateFormat = "dd"
                        let dateNow = (timestamp.stringFromDate(NSDate()) as NSString).integerValue
                        
                        for entry in arr {
                            let date = entry.objectForKey("Date") as! String
                            let dateInt = date as NSString
                            
                            let fajr = entry.objectForKey("Fajr") as! String
                            let duhr = entry.objectForKey("Duhr") as! String
                            let asr = entry.objectForKey("Asr") as! String
                            let maghreb = entry.objectForKey("Maghreb") as! String
                            let ishaa = entry.objectForKey("Ishaa") as! String
                            
                            var dict = ["Fajr" : fajr, "Duhr":duhr, "Asr":asr, "Maghreb":maghreb, "Ishaa":ishaa]
                            
                            for result in dict {
                                var dateComp:NSDateComponents = NSDateComponents()
                                var calendar:NSCalendar = NSCalendar(calendarIdentifier: NSGregorianCalendar)!
                                var dateFormatter = NSDateFormatter()
                                dateFormatter.dateFormat = "MM"
                                
                                dateComp.year = 2015
                                dateComp.month = (dateFormatter.stringFromDate(NSDate()) as NSString).integerValue
                                dateComp.day = dateInt.integerValue
                                dateComp.hour = getHour(result.1).integerValue
                                dateComp.minute = getMinutes(result.1).integerValue
                                
                                var date:NSDate = calendar.dateFromComponents(dateComp)!
                                
                                var globalPrayer:Prayer = Prayer()
                                globalPrayer.generate(result.0, time: result.1, date: date)
                                prayerController.notifications.append(globalPrayer)
                            }
                            
                            if dateInt.integerValue == dateNow {
                                var dict = ["Fajr" : fajr, "Duhr":duhr, "Asr":asr, "Maghreb":maghreb, "Ishaa":ishaa]
                                
                                for result in dict {
                                    var dateComp:NSDateComponents = NSDateComponents()
                                    var calendar:NSCalendar = NSCalendar(calendarIdentifier: NSGregorianCalendar)!
                                    var dateFormatter = NSDateFormatter()
                                    dateFormatter.dateFormat = "MM"
                                    
                                    dateComp.year = 2015
                                    dateComp.month = (dateFormatter.stringFromDate(NSDate()) as NSString).integerValue
                                    dateComp.day = dateNow
                                    dateComp.hour = getHour(result.1).integerValue
                                    dateComp.minute = getMinutes(result.1).integerValue
                                    
                                    var date:NSDate = calendar.dateFromComponents(dateComp)!
                                    
                                    var prayer:Prayer = Prayer()
                                    prayer.generate(result.0, time: result.1, date: date)
                                    prayerController.entries.append(prayer)
                                    prayerController.sort()
                                    
                                    //println("Adding: \(prayer.name) | Time: \(prayer.time) | TTL: \(prayer.timeLeft)")
                                    
                                    /*
                                    var notification:UILocalNotification = UILocalNotification()
                                    notification.category = "FIRST_CATEGORY"
                                    notification.alertBody = "\(result.0) is calling!"
                                    notification.fireDate = date as NSDate
                                    notification.alertAction = "Dismiss"
                                    notification.soundName = UILocalNotificationDefaultSoundName
                                    
                                    println("Notification date: \(notification.fireDate!)")
                                    var dateNow = NSDate()
                                    
                                    println("Date: \(dateNow)")
                                    
                                    UIApplication.sharedApplication().scheduleLocalNotification(notification)
                                    */
                                }
                                
                            }
                        }
                    }

                }
            }
        }
    }
    
    func stripDate(date:String) -> NSString {
        let range = date.rangeOfString(",")
        let space = date.rangeOfString(" ")
        
        var substring = date.substringWithRange(Range(start: space!.startIndex, end: range!.startIndex))
        substring = substring.stringByReplacingOccurrencesOfString(" ", withString: "", options: NSStringCompareOptions.LiteralSearch)
        
        return NSString((substring as NSString).length > 1 ? substring : "0\(substring)")
    }
    
    func getHour(time:String) -> NSString {
        return NSString(string: time.componentsSeparatedByString(":")[0])
    }
    
    func getMinutes(time:String) -> NSString {
        return NSString(string: time.componentsSeparatedByString(":")[1])
    }
}

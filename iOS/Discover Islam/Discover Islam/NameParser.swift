//
//  NameParser.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 15/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var nameParser:NameParser = NameParser()

class NameParser: NSObject {
    
    func parseData(){
        if let path = NSBundle.mainBundle().pathForResource("names", ofType: "json"){
            if let data = NSData(contentsOfMappedFile: path){
                var error:NSError?
                
                if let response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as? NSDictionary {
                    
                    let arr = response.objectForKey("names") as! NSArray
                    
                    for name in arr {
                        let index = name.objectForKey("Index") as! NSString
                        let arabic = name.objectForKey("Arabic") as! NSString
                        let english = name.objectForKey("English") as! NSString
                        let meaning = name.objectForKey("Meaning") as! NSString
                        
                        var nameOfAllah = NameOfAllah()
                        nameOfAllah.index = index.integerValue
                        nameOfAllah.arabic = arabic
                        nameOfAllah.english = english
                        nameOfAllah.meaning = meaning
                        
                        nameController.entries.append(nameOfAllah)
                    }
                }
            }
        }
    }
    
}

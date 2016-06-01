//
//  Interfaces.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 16/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

var interfaces:Interfaces = Interfaces()

class Interfaces {
    
    var container:UIView = UIView()
    var loadingView:UIView = UIView()
    var activity:UIActivityIndicatorView = UIActivityIndicatorView()
    var loadingTxt:UILabel = UILabel()
    
    func showView(view:UIView, msg: String) -> UIView {
        
        container.frame = view.frame
        container.center = view.center
        container.backgroundColor = UIColor.clearColor()
        
        loadingView.frame = CGRectMake(0, 0, 120, 100)
        loadingView.center = view.center
        loadingView.layer.cornerRadius = 10
        loadingView.clipsToBounds = true
        loadingView.backgroundColor = UIColor.darkGrayColor()
        
        activity.frame = CGRectMake(0, 0, 40, 20)
        activity.activityIndicatorViewStyle = .WhiteLarge
        self.activity.center = CGPointMake(self.loadingView.frame.size.width / 2,
            self.loadingView.frame.size.height / 3);
        activity.startAnimating()
        
        loadingTxt.text = msg
        loadingTxt.frame = CGRectMake(0, activity.frame.height, loadingView.frame.width, loadingView.frame.height)
        loadingTxt.textAlignment = .Center
        loadingTxt.textColor = UIColor.whiteColor()
        
        container.addSubview(loadingView)
        loadingView.addSubview(activity)
        loadingView.addSubview(loadingTxt)
        
        return container
    }
}

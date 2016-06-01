//
//  MoreVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 12/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit
import CoreData
import MessageUI
import StoreKit

class MoreVC: UITableViewController, MFMailComposeViewControllerDelegate, SKProductsRequestDelegate, SKPaymentTransactionObserver, UIPageViewControllerDataSource {
    
    @IBOutlet var register:UIButton!
    @IBOutlet var about:UIButton!
    @IBOutlet var email:UIButton!
    @IBOutlet var learnMore:UIButton!
    @IBOutlet var removeAds:UIButton!
    @IBOutlet var tourBtn:UIButton!
    
    var pageController:UIPageViewController!
    var titleArr:NSArray!
    var imgArr:NSArray!
    
    var productID:NSSet = NSSet(object: "org.envisiontechllc.discoverIslam.adRemoval")
    var requestCode:Int!
    
    var container:UIView!
    var loadingView:UIView!
    var activity:UIActivityIndicatorView!
    var loadingLbl:UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        titleArr = NSArray(objects: "Swipe to begin >>", "This is just some sample text describing the first image in the view")
        imgArr = NSArray(objects: "splash.png", "bg table.png")
    }
    
    override func viewWillAppear(animated: Bool) {
        if adManager.isPremium() {
            removeAds.setTitle("Restore Purchase", forState: .Normal)
        }
    }
    
    override func tableView(tableView: UITableView, willSelectRowAtIndexPath indexPath: NSIndexPath) -> NSIndexPath? {
        return nil
    }
    
    @IBAction func openRegistrationAlert(){
        let alert = UIAlertView(title: "How to Register", message: "If you are a mosque/organisation that would like to register for posting privileges on Discover Islam please send an email to register@evisiontechllc.org with the subject as \"Organisation Registration for Discover Islam\" with the following information: \n\n1) Organisation Name \n2) Reason for registration \n3) Desired username \n4) Desired password \n\nWe will endeavour to get back to you within 24-48 hours.", delegate: self, cancelButtonTitle: "Dismiss")
        
        alert.show()
    }
    
    @IBAction func emailDiscoverIslam(){
        let emailApp = MFMailComposeViewController()
        
        emailApp.setSubject("[Discover Islam]: ")
        emailApp.setToRecipients(["support@envisiontechllc.org"])
        emailApp.mailComposeDelegate = self
        
        presentViewController(emailApp, animated: true, completion: nil)
    }
    
    @IBAction func signOut(){
        let appDel:AppDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        let ctx:NSManagedObjectContext = appDel.managedObjectContext!
        
        let request = NSFetchRequest(entityName: "User")
        if let response:NSArray = ctx.executeFetchRequest(request, error: nil) as? [User] {
            if response.count > 0 {
                for user in response {
                    ctx.deleteObject(user as! NSManagedObject)
                    println("Deleting: \(user)")
                    adManager.user = nil
                    ctx.save(nil)
                }
            }
        }
        
        let vc = storyboard?.instantiateViewControllerWithIdentifier("LoginVC") as! LoginVC
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    @IBAction func aboutDI(){
        var alert = UIAlertView(title: "About Discover Islam", message: "Discover Islam attempts to connect brothers and sisters around the world with their local Mosque's and Organisations who are running events and fund raising. \n\nChecking Prayer times can be tedious and take long, with Discover Islam you have access to OFFLINE Prayer Times with the addition of notifications (optional). \n\nOur aim is to make an all rounded app for Muslims to access content which is relevant to their self improvement. \n\nMore features soon to come!", delegate: self, cancelButtonTitle: "Dismiss")
        
        alert.show()
    }
    
    @IBAction func aboutCharityScheme(){
        var alert = UIAlertView(title: "Make a Difference", message: "The Discover Islam team will select a charity each month, from a list of charities voted by our users and donate 50% of any proceeds from advertising and in-app purchases to that charity. \n\nThe aim of the scheme is to reach charities which make a difference to Muslim's around the world and aid them in achieving their mission and increasing our good deeds in this life.", delegate: self, cancelButtonTitle: "Dismiss")
        
        alert.show()
    }
    
    @IBAction func removeAds(sender: UIButton){
        let view = interfaces.showView(self.view, msg: "Validating")
        
        adManager.checkPremium({
            (code) in
            if SKPaymentQueue.canMakePayments() {
                println("Requesting apple product")
                dispatch_async(dispatch_get_main_queue(), {
                    view.hidden = false
                    self.view.addSubview(view)
                })
                
                if code != 1 {
                    var productRequest:SKProductsRequest = SKProductsRequest(productIdentifiers: [self.productID])
                    productRequest.delegate = self
                    productRequest.start()
                    
                    self.requestCode = code
                } else {
                    dispatch_async(dispatch_get_main_queue(), {
                        view.hidden = false
                    })
                    
                    var alert = UIAlertView(title: "Already purchased", message: "Looks like you have already purchased the abilility to remove ads. Your purchase has been restored.", delegate: self, cancelButtonTitle: "Dismiss")
                    alert.show()
                }
            } else {
                var alert = UIAlertView(title: "Unable to purchase", message: "Please ensure you have in-app purchases enabled in your Settings as well as the ability to make Payments.", delegate: self, cancelButtonTitle: "")
                alert.show()
            }
        })
    }
    
    func productsRequest(request: SKProductsRequest!, didReceiveResponse response: SKProductsResponse!) {
        if response.products.count > 0 {
            var products = response.products
            var validProduct = products[0] as! SKProduct
            
            if validProduct.productIdentifier == self.productID {
                buyProduct(validProduct)
            }
        }
    }
    
    func paymentQueue(queue: SKPaymentQueue!, updatedTransactions transactions: [AnyObject]!) {
        println("Received Payment Response from Apple")
        for transaction in transactions {
            if let trans = transaction as? SKPaymentTransaction {
                switch trans.transactionState {
                case .Purchased:
                    println("User has purchased the remove ads")
                    break
                case .Failed:
                    println("User has failed to purchase the ads")
                    break
                default:
                    println("Default case reached")
                    break
                }
            }
        }
    }
    
    func buyProduct(product:SKProduct){
        println("Sending the product to be queued")
        var payment:SKPayment = SKPayment(product: product)
        SKPaymentQueue.defaultQueue().addPayment(payment)
        
    }
    
    func mailComposeController(controller: MFMailComposeViewController!, didFinishWithResult result: MFMailComposeResult, error: NSError!) {
        dismissViewControllerAnimated(true, completion: nil)
    }

    @IBAction func takeTour(){
        self.pageController = storyboard?.instantiateViewControllerWithIdentifier("PageVC") as! UIPageViewController
        self.pageController.dataSource = self
        
        var startVC = self.viewControllerAtIndex(0) as! ContentVC
        var viewControllers = NSArray(object: startVC)
        
        self.pageController.setViewControllers(viewControllers as [AnyObject], direction: .Forward, animated: true, completion: nil)
        
        var frame = CGRectMake(view.frame.minX, view.frame.minY, view.frame.width, view.frame.height)
        self.pageController.view.frame = frame
        self.pageController.view.backgroundColor = UIColor(patternImage: UIImage(named: "login screen")!)
        
        self.presentViewController(pageController, animated: true, completion: nil)

        /*
        self.addChildViewController(self.pageController)
        self.view.addSubview(self.pageController.view)
        
        self.pageController.didMoveToParentViewController(self)
        
        var alert = UIAlertView(title: "Under construction", message: "This feature is currently unavailable due to it still being under construction.", delegate: self, cancelButtonTitle: "Dismiss")
        alert.show()
        */
    }
    
    func viewControllerAtIndex(index: Int) -> ContentVC {
        let vc = storyboard?.instantiateViewControllerWithIdentifier("ContentVC") as! ContentVC
        
        if index < 0 || index >= titleArr.count {
            vc.title = index <= 0 ? titleArr[index] as! String : "Swipe to close"
            vc.pageImg = index <= 0 ? imgArr[index] as! String : "splash.png"
            
            return vc
        }
        
        vc.pageTitle = self.titleArr[index] as! String
        vc.pageImg = self.imgArr[index] as! String
        vc.pageIndex = index
        
        return vc
    }
    
    func pageViewController(pageViewController: UIPageViewController, viewControllerAfterViewController viewController: UIViewController) -> UIViewController? {
        
        var vc = viewController as! ContentVC
        var index:Int = (vc.pageIndex != nil ? vc.pageIndex : titleArr.count)
        
        if index >= self.titleArr.count || index == NSNotFound {
            pageController.dismissViewControllerAnimated(true, completion: nil)
            return nil
        }
        
        index++
        return self.viewControllerAtIndex(index)
    }
    
    func pageViewController(pageViewController: UIPageViewController, viewControllerBeforeViewController viewController: UIViewController) -> UIViewController? {
        
        var vc = viewController as! ContentVC
        var index:Int = (vc.pageIndex != nil ? vc.pageIndex : 0)
        
        if index == 0 || index == NSNotFound {
            return nil
        }
        
        index--
        return self.viewControllerAtIndex(index)
    }
    
    func presentationCountForPageViewController(pageViewController: UIPageViewController) -> Int {
        return self.titleArr.count
    }
    
    func presentationIndexForPageViewController(pageViewController: UIPageViewController) -> Int {
        return 0
    }
}

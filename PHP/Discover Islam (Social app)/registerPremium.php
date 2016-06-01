<?php

include_once("db.php");

$user = mysqli_real_escape_string($conn, $_GET['username']);	
$checkPremium = mysqli_real_escape_string($conn, $_GET['check']);

if(isset($checkPremium) && $checkPremium == 1){
	$checkQ = "SELECT `Premium` FROM Users WHERE `Username`='$user'";
	$checkResult = mysqli_query($conn, $checkQ);
	$isPremium = array();
	
	if(mysqli_num_rows($checkResult) > 0){
		while($row = mysqli_fetch_assoc($checkResult)){
			$isPremium[] = $row;
			echo json_encode($row);
	
		}
	}
} else {
	if(isset($user)){
		$query = "UPDATE `Users` SET `Premium`='1' WHERE `Username`='$user'";
		$result = mysqli_query($conn, $query);
		
		if($result){
			//echo "Successfully turned premium <br>";
			echo json_encode(array("code"=>"200"));
		} else {
			//echo "Something went wrong <br>";
			echo mysqli_errno($conn);
		}
	}
}
mysqli_close($conn);
?>
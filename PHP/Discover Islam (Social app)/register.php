<?php
	
include_once("db.php");

$user = mysqli_real_escape_string($conn, $_GET['username']);
$pass = mysqli_real_escape_string($conn, $_GET['password']);
$email = mysqli_real_escape_string($conn, $_GET['email']);

if(isset($user) && isset($pass) && isset($email)){
	$checkExists = "SELECT * FROM `Users` WHERE `Username`='$user'";
	$result = mysqli_query($conn, $checkExists);
	
	if(mysqli_fetch_assoc($result) > 0){
		echo json_encode(array("code"=>"201"));
	} else {
		$query = "INSERT INTO Users(ID, Username, Password, Email, Premium) VALUES (null, '$user', '$pass', '$email', false)";
		$success = mysqli_query($conn, $query);
		if($success){
		echo json_encode(array("code"=>"200"));
		} else {
			echo mysqli_error($conn);
		}
	}
}

mysqli_close($conn);

?>
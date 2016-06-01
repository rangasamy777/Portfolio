<?php 

include_once("db.php");

$user = mysqli_real_escape_string($conn, $_GET['username']);
$pass = mysqli_real_escape_string($conn, $_GET['password']);
	
if(isset($user) && isset($pass)){
	$query = "SELECT * FROM `Users` WHERE `Username`='$user' AND `Password`='$pass'";
	$result = mysqli_query($conn, $query);
	$response = array("code"=>"404");
	
	if(mysqli_num_rows($result) > 0){
		while($row = mysqli_fetch_assoc($result)){
			$response = array("code"=>"1");
			$response[] = $row;
		}
	} else {
		$response = array("code"=>"0");
	}
	
	echo json_encode($response);

	mysqli_close($conn);
}
	
?>
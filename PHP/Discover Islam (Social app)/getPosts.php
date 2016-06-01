<?php

include_once("db.php");

$username = mysqli_real_escape_string($conn, $_GET['username']);	
	
if(isset($username)){
	$query = "SELECT * FROM NewsFeed WHERE `Author`='$username'";
	$result = mysqli_query($conn, $query);
	$array = array();
	
	if($result){
		while($row = mysqli_fetch_assoc($result)){
			$array[] = $row;
		}
		$json = json_encode(array('posts'=>$array));
		echo $json;
	} else {
		echo "Error!";
	}
	
	mysqli_close($conn);
}	
	
?>
<?php
	
include_once("db.php");

$username = mysqli_real_escape_string($conn, $_GET['username']);	

if(isset($username)){
	$query = "SELECT `Username`, `Likes`, `Posts`, `Location`, `ProfileImg`, `BackgroundImg` FROM Mosques WHERE `Username`='$username'";

$result = mysqli_query($conn, $query);
$array = array();

if($result){
	while($row = mysqli_fetch_assoc($result)){
		$array[] = $row;
	}
	$json = json_encode(array('profile'=>$array));
	echo($json);
	
	mysqli_close($conn);
} else {
	echo "Error querying data";
}


}
?>
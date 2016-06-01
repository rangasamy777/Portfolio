<?php

include_once("db.php");

$query = "SELECT * FROM `NewsFeed`";
$result = mysqli_query($conn, $query);
$postArray = array();

if($result){
	
	while($res = mysqli_fetch_assoc($result)){
		$postArray[] = $res;
	}
	$json = json_encode(array('posts'=>$postArray));
	echo $json;
} else {
	echo "No data retrieved from the SQL Database";
}

mysqli_close($conn);

?>
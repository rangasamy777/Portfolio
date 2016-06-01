<?php

include_once("db.php");

$postID = mysqli_real_escape_string($conn, $_GET['postID']);

if(isset($postID)){
	$query = "SELECT `Likes` FROM NewsFeed WHERE `ID`=$postID";
	$result = mysqli_query($conn, $query);
	
	if($result){
		while($row = mysqli_fetch_assoc($result)){
			$likes = $row["Likes"];
			$likes = $likes + 1;
			$update = "UPDATE `NewsFeed` SET `Likes`=$likes WHERE `ID`=$postID";
			$res = mysqli_query($conn, $update);
			if($res){
				$json = json_encode("Success");
			} else {
				$json = json_encode("Failed");
			}
			echo $json;
		}
	}
}

mysqli_close($conn);

?>
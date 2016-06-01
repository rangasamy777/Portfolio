<?php

$host = "localhost";
$username = "envis722_islam";
$password = "DiscoverIslam2015";
$db = "envis722_islam";

$conn = mysqli_connect($host, $username, $password, $db);

if(!$conn){
	echo mysqli_errorno();
}

?>
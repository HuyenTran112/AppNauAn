<?php
	require "dbConnect.php";
	$mamonan=$_POST['mamonan'];
	$query="DELETE FROM monan WHERE mamonan='$mamonan'";
	if(mysqli_query($connection,$query))
	{
		//thành công
		echo "success";
	}
	else
	{
		//lỗi
		echo "error";
	}
?>
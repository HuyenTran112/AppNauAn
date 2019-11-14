<?php
//kết nối database
require "dbConnect.php";
$email=$_POST['email'];
$matkhau=$_POST['matkhau'];
// $email="huyentranbui112@gmail.com";
//$matkhau="123456";
//global $email_str:=$email;
$email="abc@gmail.com";
$query="SELECT * FROM nguoidung where email='$email' and matkhau='$matkhau'";
$data=mysqli_query($connection,$query);
if(mysqli_num_rows($data)>0)
	echo "success";
else
	echo "error";

?>
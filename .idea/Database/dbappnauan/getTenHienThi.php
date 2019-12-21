<?php
//kết nối database
require "dbConnect.php";
//$email=$_POST['email'];
//$matkhau=$_POST['matkhau'];
$email="huyentranbui112@gmail.com";
$matkhau="123456";
$query="SELECT tenhienthi FROM nguoidung where email='$email' and matkhau='$matkhau'";
$data=mysqli_query($connection,$query);
while ($row = $data->fetch_assoc())
{
    foreach($row as $value) echo $value;
}

?>
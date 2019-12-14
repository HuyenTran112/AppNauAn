<?php
//kết nối database
require "dbConnect.php";

class MonAn{
	function MonAn($mamonan,$tenmonan,$nguyenlieu, $congthuc, $hinhanh, $maloaimonan,$manguoidung)
	{
		$this->mamonan=$mamonan;
		$this->tenmonan=$tenmonan;
		$this->nguyenlieu=$nguyenlieu;
		$this->congthuc=$congthuc;
		$this->hinhanh=$hinhanh;
		$this->maloaimonan=$maloaimonan;
		$this->manguoidung=$manguoidung;
	}
}

$mangMonAn = array();

if (isset($_POST['key'])){
	$key = $_POST['key'];
	// $key = "l";
	$query = "SELECT * FROM monan, loaimonan WHERE monan.maloaimonan = loaimonan.maloaimonan AND (lower(tenmonan) LIKE '%$key%' OR lower(tenloaimonan) LIKE '%$key%')";
	$data=mysqli_query($connection,$query);

	while($row=mysqli_fetch_assoc($data))
	{
		array_push($mangMonAn,new MonAn($row['mamonan'],$row['tenmonan'],$row['nguyenlieu'],$row['congthuc'], $row['hinhanh'],$row['maloaimonan'],$row['manguoidung']));
	}
	echo json_encode($mangMonAn);
}
?>
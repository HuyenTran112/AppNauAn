<?php
require 'dbConnect.php';
global $connection;

//response array

        $mamonan = (int)$_POST['mamonan'];
        $tenmonan = $_POST['tenmonan'];
        $nguyenlieu=$_POST['nguyenlieu'];
        $congthuc=$_POST['congthuc'];
        $maloaimonan=$_POST['maloaimonan'];


            
             	$sql = "UPDATE monan SET tenmonan = '$tenmonan', nguyenlieu = '$nguyenlieu', congthuc = '$congthuc', maloaimonan = $maloaimonan WHERE mamonan = $mamonan";          
    
            	if(mysqli_query($connection,$sql)){
                //filling response array with values
                    echo "success";              	
            	}
                else echo "error";
            //if some error occurred
        	

?>
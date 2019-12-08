<?php
require 'dbConnect.php';
global $connection;
$upload_path = 'imageuser/'; //this is our upload folder
$server_ip = "172.17.29.121:8080"; //Getting the server ip
$upload_url = 'http://'.$server_ip.'/AppNauAn/Database/dbappnauan/'.$upload_path;
//response array
$response = array();
 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
    //checking the required parameters from the request
    if(isset($_POST['email']) and isset($_POST['tenhienthi']) and isset($_POST['matkhau']) and isset($_POST['maloainguoidung']))
    {
        $email=$_POST['email'];
        $tenhienthi=$_POST['tenhienthi'];
        $matkhau=$_POST['matkhau'];
        $maloainguoidung=$_POST['maloainguoidung'];
        $fileinfo = pathinfo($_FILES['image']['name']);//getting file info from the request
        $extension = $fileinfo['extension']; //getting the file extension
        $file_url = $upload_url . getFileName() . '.' . $extension; //file url to store in the database
        $file_path = $upload_path . getFileName() . '.'. $extension; //file path to upload in the server
        $img_name = getFileName() . '.'. $extension; //file name to store in the database
 
        try{
            move_uploaded_file($_FILES['image']['tmp_name'],$file_path); //saving the file to the uploads folder;
           
            //adding the path and name to database
            // $sql = "INSERT INTO monan(tenmonan,nguyenlieu , congthuc,hinhanh,maloaimonan,manguoidung) ";
            // $sql .= "VALUES ('{$tenmonan}', '{$nguyenlieu}', '{$congthuc}','{$file_url},'{$maloaimonan}','{$manguoidung}');";
             $sql = "INSERT INTO nguoidung(manguoidung, email, tenhienthi,matkhau,maloainguoidung, hinhanh) VALUES (null, '$email','$tenhienthi','$matkhau','$maloainguoidung','$file_url')";
    
            if(mysqli_query($connection,$sql)){
                //filling response array with values
                $response['error'] = false;
                $response['email'] = $email;
                $response['tenhienthi'] = $tenhienthi;
                $response['matkhau'] = $matkhau;
                $response['hinhanh']=$file_url;
                $response['maloainguoidung']=$maloainguoidung;
            }
            //if some error occurred
        }catch(Exception $e){
            $response['error']=true;
            $response['message']=$e->getMessage();
        }
        //displaying the response
        echo json_encode($response);
 
        //closing the connection
        mysqli_close($connection);
    }else{
        $response['error'] = true;
        $response['message']='Please choose a file';
    }
}
 
/*
We are generating the file name
so this method will return a file name for the image to be uploaded
*/
function getFileName(){
    global $connection;
     
    $sql = "SELECT max(manguoidung) as manguoidung FROM nguoidung";
    $result = mysqli_fetch_array(mysqli_query($connection, $sql));
 
    if($result['manguoidung']== null)
        return 1;
    else
        return ++$result['manguoidung'];
     
    mysqli_close($connection);
}
?>
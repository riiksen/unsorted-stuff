<?php
$url = 'http://localhost:31337/withdraw';

$opts = array(
	'http' => array(
		'method' => "POST",
		'header' => "Content-type: application/json\r\n",
		'content' => http_build_query(array(
			'token' => "", //Withdraw aauthentication token here
			'partner' => "", //Partner address Here
			'total_value' => 2.0, //Float value in usd
			'items' => array( //Items to withdraw
				array()
			)
		))
	)
);

$response = file_get_contents($url, false, stream_context_create($opts));
if($resopnse) { //Handle Error or not
	echo $resopnse;
}
?>
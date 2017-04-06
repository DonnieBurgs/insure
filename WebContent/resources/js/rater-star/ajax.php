<?php

echo "请求方式:";
echo ($_POST)?'POST':'GET';
echo "\n第{$_REQUEST['number']}颗星星，值为{$_REQUEST['value']}";
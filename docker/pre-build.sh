docker network create --subnet 172.16.0.0/16 mysqlnet

docker run --rm --network mysqlnet -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=test --ip 172.16.0.10 --name mysql -d 192.168.0.220:6789/mysql:5

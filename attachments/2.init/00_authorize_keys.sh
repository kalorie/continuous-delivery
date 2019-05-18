hosts=`cat ../0.common/host-master ../0.common/host-minion`
for h in $hosts
do
    ssh-copy-id -i ~/.ssh/ops.pub root@$h
done

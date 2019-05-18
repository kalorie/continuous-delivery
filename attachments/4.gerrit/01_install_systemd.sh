pscp -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master gerrit-docker.service /etc/systemd/system/gerrit-docker.service

pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -i "systemctl enable gerrit-docker"

pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -i "systemctl restart gerrit-docker"
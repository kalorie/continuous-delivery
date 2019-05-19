pscp -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master hoverfly-docker.service /etc/systemd/system/hoverfly-docker.service

pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -i "systemctl enable hoverfly-docker"

pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -i "systemctl restart hoverfly-docker"

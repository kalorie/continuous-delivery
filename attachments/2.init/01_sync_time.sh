pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -h ../0.common/host-minion -i "yum install -y ntp --disablerepo=k8s,docker-ce-stable"
pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -h ../0.common/host-minion -i "systemctl enable ntpd"
pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -h ../0.common/host-minion -i "systemctl start ntpd"
pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -h ../0.common/host-minion -i "ntpdate -u ntp.api.bz"

pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-master -i "curl -s -L https://bootstrap.saltstack.com | bash -s - -M -A 192.168.0.5"
pssh -l root -O IdentityFile=~/.ssh/ops -h ../0.common/host-minion -i "curl -s -L https://bootstrap.saltstack.com | bash -s - -A 192.168.0.5"

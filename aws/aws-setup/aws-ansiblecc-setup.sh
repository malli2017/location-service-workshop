#!/usr/bin/env bash

echo "creating .ssh directory ..."
cd /home/ubuntu
mkdir -p .ssh
echo "Installing the workshop's public key, use the private key with putty or openssh"
echo "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC7ymBRUodtreE3trfzmhT92VqV1FlvoE60InQM1FXMBE1S30eAuUgg928LvhzStB6GSwuYzQpfMIU+WafeSj2j3azPfwUrTBlKpLnYTBc9FuC41kYUooC+nvEDUouSEgCZzZDGThtoTNB7R3bB8sc3grI5/4xi832NsHJU5ma/gd64nv2iJnOcXQcDHC60OZDJVjt1GaowypviWSv1Uchpr3holyJ7JO1WT7ZvljHqMmJp0bhnerupR1x/xRNVwNBIxOkmBZDkEcXwjypoif20sEvHr7UW0FWa0SkNF9Vs81ymkhlD5Yla94hBZhA3otQ5VX1R3zu1AE3HqculmA6V workshop-keypair" >> .ssh/authorized_keys
echo "Installs git and the latest version of Ansible"
sudo apt-get update
sudo apt-get install git git-extras software-properties-common build-essential libffi-dev libssl-dev python-pip python-dev -y
sudo pip install pip --upgrade
sudo pip install setuptools boto botocore boto3 markupsafe ansible awscli

echo "Cloning location-service-workshop"
git clone https://github.com/toefel18/location-service-workshop.git

sudo chown ubuntu. -R location-service-workshop

echo "using the workshop_ansiblecc_key as personal id for ubuntu user"
cp /home/ubuntu/location-service-workshop/aws/aws-setup/workshop_ansiblecc_key /home/ubuntu/.ssh/id_rsa
chown ubuntu. /home/ubuntu/.ssh/id_rsa
chmod 400 /home/ubuntu/.ssh/id_rsa

echo "disabling ansible host checking"
sudo sh -c 'echo "export ANSIBLE_HOST_KEY_CHECKING=False" > /etc/profile.d/ansible-cfg.sh'
sudo chmod 755 /etc/profile.d/ansible-cfg.sh

sudo ansible-galaxy install -r location-service-workshop/aws/ansible/requirements.yml
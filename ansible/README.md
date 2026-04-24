# Ansible deployment

## Prerequisites
- Ansible installed locally (`ansible --version`)
- SSH private key at `~/.ssh/i` with permissions `400`
- Access to the Takima server

## Test connectivity
ansible all -id_rsa inventories/prod/setup.yml -m ping

## Run the full playbook (coming next)
ansible-playbook -i inventories/prod/setup.yml playbook.yml
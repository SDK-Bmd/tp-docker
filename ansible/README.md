# Ansible deployment

## Prerequisites
- Ansible installed locally (`ansible --version`)
- SSH private key at `~/.ssh/takima_key` with permissions `400`
- Access to the Takima server

## Test connectivity
```bash
ansible all -i inventories/prod/setup.yml -m ping
```

## Run the full playbook
```bash
ansible-playbook -i inventories/prod/setup.yml playbook.yml --ask-vault-pass
```
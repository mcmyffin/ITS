#apt-get --assume-yes install openvpn
#apt-get --assume-yes install git
#
#cp cp /media/root/2dd6c774-de9d-4f83-b4fd-50b640ac55d2/dima/.ssh/#id_rsa_docker ~/.ssh/id_rsa
#cp /media/root/2dd6c774-de9d-4f83-b4fd-50b640ac55d2/dima/.ssh/#id_rsa_docker.pub ~/.ssh/id_rsa.pub
#
#cp /media/root/2dd6c774-de9d-4f83-b4fd-50b640ac55d2/dima/Downloads/#abp615.ovpn ~/abp615.ovpn
#openvpn ~abp615.ovpn&
#
#ssh root@172.18.0.24
#
#--- im Docker -----
# installiere apache2
apt-get --assume-yes install apache2 nano git

/etc/init.d/apache2 start
# === Versuch 2 ====

# git repro clonen
git clone https://bitbucket.org/stefanholek/pki-example-1
cd pki-example-1

mkdir -p ca/root-ca/private ca/root-ca/db crl certs
chmod 700 ca/root-ca/private

cp /dev/null ca/root-ca/db/root-ca.db
cp /dev/null ca/root-ca/db/root-ca.db.attr
echo 01 > ca/root-ca/db/root-ca.crt.srl
echo 01 > ca/root-ca/db/root-ca.crl.srl

# root-ca.conf und signing-ca.conf ersetzen 
echo '

# Simple Root CA

# The [default] section contains global constants that can be referred to from
# the entire configuration file. It may also hold settings pertaining to more
# than one openssl command.

[ default ]
ca                      = root-ca               # CA name
dir                     = .                     # Top dir

# The next part of the configuration file is used by the openssl req command.
# It defines the CAs key pair, its DN, and the desired extensions for the CA
# certificate.

[ req ]
default_bits            = 2048                  # RSA key size
encrypt_key             = yes                   # Protect private key
default_md              = sha1                  # MD to use
utf8                    = yes                   # Input is UTF-8
string_mask             = utf8only              # Emit UTF-8 strings
prompt                  = no                    # Dont prompt for DN
distinguished_name      = ca_dn                 # DN section
req_extensions          = ca_reqext             # Desired extensions

[ ca_dn ]
0.domainComponent       = "DE"
1.domainComponent	= "haw-hamburg"
organizationName        = "haw-hamburg"
commonName              = "CA"

[ ca_reqext ]
keyUsage                = critical,keyCertSign,cRLSign
basicConstraints        = critical,CA:true
subjectKeyIdentifier    = hash

# The remainder of the configuration file is used by the openssl ca command.
# The CA section defines the locations of CA assets, as well as the policies
# applying to the CA.

[ ca ]
default_ca              = root_ca               # The default CA section

[ root_ca ]
certificate             = $dir/ca/$ca.crt       # The CA cert
private_key             = $dir/ca/$ca/private/$ca.key # CA private key
new_certs_dir           = $dir/ca/$ca           # Certificate archive
serial                  = $dir/ca/$ca/db/$ca.crt.srl # Serial number file
crlnumber               = $dir/ca/$ca/db/$ca.crl.srl # CRL number file
database                = $dir/ca/$ca/db/$ca.db # Index file
unique_subject          = no                    # Require unique subject
default_days            = 3652                  # How long to certify for
default_md              = sha1                  # MD to use
policy                  = match_pol             # Default naming policy
email_in_dn             = no                    # Add email to cert DN
preserve                = no                    # Keep passed DN ordering
name_opt                = ca_default            # Subject DN display options
cert_opt                = ca_default            # Certificate display options
copy_extensions         = none                  # Copy extensions from CSR
x509_extensions         = signing_ca_ext        # Default cert extensions
default_crl_days        = 365                   # How long before next CRL
crl_extensions          = crl_ext               # CRL extensions

# Naming policies control which parts of a DN end up in the certificate and
# under what circumstances certification should be denied.

[ match_pol ]
domainComponent         = match                 # Must match "simple.org"
organizationName        = match                 # Must match "Simple Inc"
organizationalUnitName  = optional              # Included if present
commonName              = supplied              # Must be present

[ any_pol ]
domainComponent         = optional
countryName             = optional
stateOrProvinceName     = optional
localityName            = optional
organizationName        = optional
organizationalUnitName  = optional
commonName              = optional
emailAddress            = optional

# Certificate extensions define what types of certificates the CA is able to
# create.

[ root_ca_ext ]
keyUsage                = critical,keyCertSign,cRLSign
basicConstraints        = critical,CA:true
subjectKeyIdentifier    = hash
authorityKeyIdentifier  = keyid:always

[ signing_ca_ext ]
keyUsage                = critical,keyCertSign,cRLSign
basicConstraints        = critical,CA:true,pathlen:0
subjectKeyIdentifier    = hash
authorityKeyIdentifier  = keyid:always

# CRL extensions exist solely to point to the CA certificate that has issued
# the CRL.

[ crl_ext ]
authorityKeyIdentifier  = keyid:always


' > ~/pki-example-1/etc/root-ca.conf

echo '

# Simple Signing CA

# The [default] section contains global constants that can be referred to from
# the entire configuration file. It may also hold settings pertaining to more
# than one openssl command.

[ default ]
ca                      = signing-ca            # CA name
dir                     = .                     # Top dir

# The next part of the configuration file is used by the openssl req command.
# It defines the CAs key pair, its DN, and the desired extensions for the CA
# certificate.

[ req ]
default_bits            = 2048                  # RSA key size
encrypt_key             = yes                   # Protect private key
default_md              = sha1                  # MD to use
utf8                    = yes                   # Input is UTF-8
string_mask             = utf8only              # Emit UTF-8 strings
prompt                  = no                    # Dont prompt for DN
distinguished_name      = ca_dn                 # DN section
req_extensions          = ca_reqext             # Desired extensions

[ ca_dn ]
0.domainComponent       = "DE"
1.domainComponent       = "haw-hamburg"
organizationName        = "haw-hamburg"
organizationalUnitName  = "informatik"
commonName              = "CA "

[ ca_reqext ]
keyUsage                = critical,keyCertSign,cRLSign
basicConstraints        = critical,CA:true,pathlen:0
subjectKeyIdentifier    = hash

# The remainder of the configuration file is used by the openssl ca command.
# The CA section defines the locations of CA assets, as well as the policies
# applying to the CA.

[ ca ]
default_ca              = signing_ca            # The default CA section

[ signing_ca ]
certificate             = $dir/ca/$ca.crt       # The CA cert
private_key             = $dir/ca/$ca/private/$ca.key # CA private key
new_certs_dir           = $dir/ca/$ca           # Certificate archive
serial                  = $dir/ca/$ca/db/$ca.crt.srl # Serial number file
crlnumber               = $dir/ca/$ca/db/$ca.crl.srl # CRL number file
database                = $dir/ca/$ca/db/$ca.db # Index file
unique_subject          = no                    # Require unique subject
default_days            = 730                   # How long to certify for
default_md              = sha1                  # MD to use
policy                  = match_pol             # Default naming policy
email_in_dn             = no                    # Add email to cert DN
preserve                = no                    # Keep passed DN ordering
name_opt                = ca_default            # Subject DN display options
cert_opt                = ca_default            # Certificate display options
copy_extensions         = copy                  # Copy extensions from CSR
x509_extensions         = email_ext             # Default cert extensions
default_crl_days        = 7                     # How long before next CRL
crl_extensions          = crl_ext               # CRL extensions

# Naming policies control which parts of a DN end up in the certificate and
# under what circumstances certification should be denied.

[ match_pol ]
domainComponent         = supplied              # Must match "simple.org"
organizationName        = supplied              # Must match "Simple Inc"
organizationalUnitName  = optional              # Included if present
commonName              = supplied              # Must be present

[ any_pol ]
domainComponent         = optional
countryName             = optional
stateOrProvinceName     = optional
localityName            = optional
organizationName        = optional
organizationalUnitName  = optional
commonName              = optional
emailAddress            = optional

# Certificate extensions define what types of certificates the CA is able to
# create.

[ email_ext ]
keyUsage                = critical,digitalSignature,keyEncipherment
basicConstraints        = CA:false
extendedKeyUsage        = emailProtection,clientAuth
subjectKeyIdentifier    = hash
authorityKeyIdentifier  = keyid:always

[ server_ext ]
keyUsage                = critical,digitalSignature,keyEncipherment
basicConstraints        = CA:false
extendedKeyUsage        = serverAuth,clientAuth
subjectKeyIdentifier    = hash
authorityKeyIdentifier  = keyid:always

# CRL extensions exist solely to point to the CA certificate that has issued
# the CRL.

[ crl_ext ]
authorityKeyIdentifier  = keyid:always


' > ~/pki-example-1/etc/signing-ca.conf


# Root Zertifikat erstellen
openssl req -new \
    -config etc/root-ca.conf \
    -out ca/root-ca.csr \
    -keyout ca/root-ca/private/root-ca.key
# pw
#sadim
#pw repeat
#sadim

# Selfsign
openssl ca -selfsign \
    -config etc/root-ca.conf \
    -in ca/root-ca.csr \
    -out ca/root-ca.crt \
    -extensions root_ca_ext

# pw
#sadim

# yes


# yes 2


mkdir -p ca/signing-ca/private ca/signing-ca/db crl certs
chmod 700 ca/signing-ca/private

cp /dev/null ca/signing-ca/db/signing-ca.db
cp /dev/null ca/signing-ca/db/signing-ca.db.attr
echo 01 > ca/signing-ca/db/signing-ca.crt.srl
echo 01 > ca/signing-ca/db/signing-ca.crl.srl

# request signing ca
openssl req -new \
    -config etc/signing-ca.conf \
    -out ca/signing-ca.csr \
    -keyout ca/signing-ca/private/signing-ca.key

#pw
#sadim

#pw repeat
#sadim

# create ca certificate
openssl ca \
    -config etc/root-ca.conf \
    -in ca/signing-ca.csr \
    -out ca/signing-ca.crt \
    -extensions signing_ca_ext

# pw
#sadim

# yes


#yes 2


# Create TLS server request
SAN=DNS:sadim.informatik.haw-hamburg.de \
openssl req -new \
    -config etc/server.conf \
    -out certs/simple.org.csr \
    -keyout certs/simple.org.key

# 1. Domain Component         (eg, com)       []:
#DE

# 2. Domain Component         (eg, company)   []:
#haw-hamburg

# 3. Domain Component         (eg, pki)       []:
#informatik

# 4. Organization Name        (eg, company)   []:
#haw-hamburg

# 5. Organizational Unit Name (eg, section)   []:
#informatik

# 6. Common Name              (eg, FQDN)      []:
#sadim.informatik.haw-hamburg.de



# create TLS server certificate
openssl ca \
    -config etc/signing-ca.conf \
    -in certs/simple.org.csr \
    -out certs/simple.org.crt \
    -extensions server_ext 
#pw
#sadim
# yes

# yes2


# ssl in apache2 aktivieren
a2enmod ssl
#
# apache2 neustarten
service apache2 restart

echo "
<VirtualHost *:443>
    SSLEngine on
    SSLCertificateFile /etc/ssl/certs/apache.crt # simple.org.crt
    SSLCertificateKeyFile /etc/ssl/private/apache.key # simple.org.key
    SSLCertificateChainFile /etc/ssl/certs/chain.crt # signing.crt
    
    # Pfad zu den Webinhalten
    DocumentRoot /var/www/
</VirtualHost>
" >> /etc/apache2/sites-available/ssl.conf 

#cp ~/pki-example-1/certs/simple.org.crt /etc/ssl/certs/apache.crt
#cp ~/pki-example-1/certs/simple.org.key /etc/ssl/private/apache.key
#cp ~/pki-example-1/ca/signing-ca.crt /etc/ssl/certs/chain.crt



# ZUM ENDE!!!
# root Zertifikat im browser importieren (root-ca.crt)
# echo "172.18.0.24 sadim.informatik.haw-hamburg.de" >> /etc/hosts 

CREATE SEQUENCE IF NOT EXISTS artifacts_id_seq;

CREATE TABLE artifacts (
	id INT NOT NULL DEFAULT nextval('artifacts_id_seq') PRIMARY KEY,
    file_path VARCHAR(255),
    hash BYTEA,
    user_id VARCHAR(255) NOT NULL
);

INSERT INTO artifacts (file_path, hash, user_id) VALUES ('C:/Windows/System32/svchost.exe', decode('4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1', 'hex'), '123-abc');
INSERT INTO artifacts (file_path, hash, user_id) VALUES ('C:/Windows/System32/lsass.exe', decode('4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f2', 'hex'), '123-abc');
INSERT INTO artifacts (file_path, hash, user_id) VALUES ('C:/Windows/System32/netsh.exe', decode('4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f3', 'hex'), '123-abc');

CREATE SEQUENCE IF NOT EXISTS common_target_files_id_seq;

CREATE TABLE common_target_files (
    id INT NOT NULL DEFAULT nextval('common_target_files_id_seq') PRIMARY KEY,
    file_path VARCHAR(255)
);

INSERT INTO common_target_files (file_path) VALUES
('C:/Windows/System32/svchost.exe'),
('C:/Windows/System32/lsass.exe'),
('C:/Windows/System32/netsh.exe'),
('C:/Windows/System32/ntoskrnl.exe'),
('C:/Windows/System32/kernel32.dll'),
('C:/Windows/System32/hal.dll'),
('C:/Windows/System32/user32.dll'),
('C:/Windows/System32/winlogon.exe'),
('C:/Windows/System32/dllhost.exe'),
('C:/Windows/System32/spoolsv.exe'),
('C:/Windows/System32/rundll32.exe'),
('C:/Windows/System32/msconfig.exe'),
('C:/Windows/System32/wininit.exe'),
('C:/Windows/System32/iexplore.exe'),
('C:/Windows/System32/explorer.exe'),
('C:/Windows/System32/taskmgr.exe'),
('C:/Windows/System32/svchost.exe'),
('C:/Windows/System32/config/systemprofile'),
('C:/Windows/System32/wuauserv'),
('C:/Windows/System32/driverstore/fileRepository'),
('C:/Windows/System32/mspmsnsv.dll'),
('C:/Windows/System32/msdtc.exe'),
('C:/Windows/System32/mstsc.exe'),
('C:/Windows/System32/rdpinit.exe'),
('C:/Windows/System32/sysprep.exe'),
('C:/Windows/System32/sfc.exe'),
('C:/Windows/System32/dcomcnfg.exe'),
('C:/Windows/System32/spooler'),
('C:/Windows/System32/sysinternals'),
('C:/Windows/System32/cmd.exe'),
('C:/Windows/System32/conhost.exe'),
('C:/Windows/System32/netstat.exe'),
('C:/Windows/System32/msiexec.exe'),
('C:/Windows/System32/mspmsnsv.dll'),
('C:/Windows/System32/cng.sys'),
('C:/Windows/System32/dnsapi.dll'),
('C:/Windows/System32/dns.exe'),
('C:/Windows/System32/tcpip.sys'),
('C:/Windows/System32/wscsvc.dll'),
('C:/Windows/System32/wininet.dll'),
('C:/Windows/System32/npkex.dll'),
('C:/Windows/System32/msvcr100.dll'),
('C:/Windows/System32/bootmgr'),
('C:/Windows/System32/vmcompute.exe'),
('C:/Windows/System32/ntkrnlpa.exe'),
('C:/Windows/System32/WindowsPowerShell/v1.0/powershell.exe'),
('C:/Windows/System32/WBEM/WbemCore.dll'),
('C:/Windows/System32/diagnostic/dtc.dll'),
('C:/Windows/System32/security/selinux'),
('C:/Windows/System32/drivers/usbccgp.sys');
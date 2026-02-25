import os
import re
import shutil

src_dir = "src/main/java/pages"

manager_pages = ["AddCustomerPage.java", "CustomersPage.java", "ManagerPage.java", "OpenAccountPage.java"]
customer_pages = ["CustomerAccountPage.java", "CustomerDepositPage.java", "CustomerLoginPage.java", "CustomerTransactionsPage.java", "CustomerWithdrawlPage.java"]

os.makedirs(os.path.join(src_dir, "manager"), exist_ok=True)
os.makedirs(os.path.join(src_dir, "customer"), exist_ok=True)

for p in manager_pages:
    src_file = os.path.join(src_dir, p)
    if os.path.exists(src_file):
        shutil.move(src_file, os.path.join(src_dir, "manager", p))

for p in customer_pages:
    src_file = os.path.join(src_dir, p)
    if os.path.exists(src_file):
        shutil.move(src_file, os.path.join(src_dir, "customer", p))

def replace_in_file(filepath, callback):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    new_content = callback(content)
    if content != new_content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)

for root, _, files in os.walk("src"):
    for file in files:
        if file.endswith(".java"):
            filepath = os.path.join(root, file)
            def update(content):
                c = content
                if "pages/manager" in filepath.replace("\\", "/"):
                    c = re.sub(r'package\s+pages;', r'package pages.manager;', c)
                elif "pages/customer" in filepath.replace("\\", "/"):
                    c = re.sub(r'package\s+pages;', r'package pages.customer;', c)

                for pp in manager_pages:
                    clz = pp.replace(".java", "")
                    c = re.sub(r'import\s+pages\.' + clz + r';', r'import pages.manager.' + clz + r';', c)
                    
                for pp in customer_pages:
                    clz = pp.replace(".java", "")
                    c = re.sub(r'import\s+pages\.' + clz + r';', r'import pages.customer.' + clz + r';', c)
                
                def ensure_import(cont, cls_name, pkg):
                    if ("package " + pkg + ";") in cont:
                        return cont
                    if ("import " + pkg + "." + cls_name + ";") in cont:
                        return cont
                    if re.search(r'\b' + cls_name + r'\b', cont):
                        # Add import after package declaration
                        cont = re.sub(r'(package\s+[a-zA-Z0-9_.]+;)', r'\1\nimport ' + pkg + '.' + cls_name + ';', cont, count=1)
                    return cont

                for pp in manager_pages:
                    c = ensure_import(c, pp.replace(".java", ""), "pages.manager")
                for pp in customer_pages:
                    c = ensure_import(c, pp.replace(".java", ""), "pages.customer")
                    
                return c
            
            replace_in_file(filepath, update)

print("done")

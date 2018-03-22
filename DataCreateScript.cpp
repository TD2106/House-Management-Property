#include <bits/stdc++.h>
#define bug(x) cout << #x << " = " << x << endl
#define pb push_back
using namespace std;
typedef long long int ll;
typedef pair<int,int> pii;
string addressBuiding[6]={"","61 Wellfield Road","14 Tottenham Court Road","3 Edgar Buildings","44-46 Morningside Road","91 Western Road"};
string firstName[5]={"","Liam","Mason","Taylor","Sofia"};
string lastName[11]={"","SMITH","JOHNSON","WILLIAMS","BROWN","JONES","MILLER","DAVIS","GARCIA","RODRIGUEZ","WILSON"};
int used[7][7][7]={0};
int bedroom[7][7][7];
int bathroom[7][7][7];
int idAgree[7][7][7]={0};
pii stay[41];
map< int,vector<int> > manage;
set<string> phone;
set<string> bank;
int role[41]={0};
string generatePhoneNumber(){
    while(1){
        string Ti="";
        for(int i=1;i<=10;i++){
            char temp='0'+abs(rand())%10;
            Ti+=temp;
        }
        if(!phone.count(Ti)){
             phone.insert(Ti);
             return Ti;
        }
    }
}
string generateBankAcc(){
    string b[3]={"OCB","SCB","ACB"};
    while(1){
        string Ti="";
        int tmp=abs(rand()%3);
        Ti+=b[tmp];
        for(int i=1;i<=10;i++){
            char temp='0'+abs(rand())%10;
            Ti+=temp;
        }
        if(!bank.count(Ti)){
             bank.insert(Ti);
             return Ti;
        }
    }
}
int findMana(int building){
    map<int, vector<int> >::iterator itr;
    for(itr=manage.begin();itr!=manage.end();itr++){
        for(int i=0;i<(itr->second).size();i++){
            if((itr->second)[i]==building){
                return itr->first;
            }
        }
    }
}
int main(){
    srand(time(NULL));
    freopen("data.txt","w",stdout);
    cout<<"Building\n\n";
    char c='A'-1;
    cout<<"INSERT INTO Building(ID_Building,Address,name,numRoom)\n";
    cout<<"VALUES ";
    for(int i=1;i<=5;i++){
        if(i!=5) cout<<"("<<i<<",'"<<addressBuiding[i]<<"','"<<char(c+i)<<"',"<<25<<"),"<<endl;
        else cout<<"("<<i<<",'"<<addressBuiding[i]<<"','"<<char(c+i)<<"',"<<25<<");"<<endl;
    }
    cout<<"====================\n";
    cout<<"Apartment\n\n";
    cout<<"INSERT INTO Apartment(ID_Apartment,ID_Building,numBedroom,numBathroom)\n";
    cout<<"VALUES ";
    for(int i=1;i<=5;i++){
        for(int j=1;j<=5;j++){
            for(int k=1;k<=5;k++){
                bedroom[i][j][k]=abs(rand())%3+1;
                bathroom[i][j][k]=abs(rand())%3+1;
                if(i==5&&j==5&&k==5) cout<<"("<<j<<k<<","<<i<<","<<bedroom[i][j][k]<<","<<bathroom[i][j][k]<<");"<<endl<<endl;
                else cout<<"("<<j<<k<<","<<i<<","<<bedroom[i][j][k]<<","<<bathroom[i][j][k]<<"),"<<endl;
                /*cout<<"ID_Apartment: "<<j<<k<<endl;
                cout<<"ID_Building: "<<i<<endl;
                cout<<"NumBedroom: "<<abs(rand())%5+1<<endl;
                cout<<"NumBathroom: "<<abs(rand())%5+1<<endl<<endl;*/
            }
        }
    }
    cout<<"====================\n";
    cout<<"Person\n\n";
    cout<<"INSERT INTO Person(SSN,First_name,Last_name)\n";
    cout<<"VALUES ";
    for(int i=1;i<=4;i++){
        for(int j=1;j<=10;j++){
            if(i==4&&j==10) cout<<"("<<(i-1)*10+j<<",'"<<firstName[i]<<"','"<<lastName[j]<<"');"<<endl<<endl;
            else cout<<"("<<(i-1)*10+j<<",'"<<firstName[i]<<"','"<<lastName[j]<<"'),"<<endl;
        }
    }
    //cout<<123<<endl;
    cout<<"====================\n";
    cout<<"PhoneNumber\n\n";
    cout<<"INSERT INTO PhoneNumber(PhoneNumber,SSN)\n";
    cout<<"VALUES ";
    for(int i=1;i<=40;i++){
        int tmp = abs(rand()%2)+1;
        if(i==40) tmp=1;
        while(tmp--){
            string temp=generatePhoneNumber();
            if(i!=40) cout<<"('"<<temp<<"',"<<i<<"),\n";
            else cout<<"('"<<temp<<"',"<<i<<");\n\n";
        }

    }
    cout<<"====================\n";
    cout<<"Employee\n\n";
    cout<<"INSERT INTO Employee(Person_SSN,Salary)\n";
    cout<<"VALUES ";
    for(int i=0;i<3;i++){
        int temp = abs(rand())%40+1;
        while(role[temp]!=0) temp = abs(rand())%40+1;
        role[temp]=1;
        cout<<"("<<temp<<",5000),\n";
    }
    for(int i=0;i<5;i++){
        int temp = abs(rand())%40+1;
        while(role[temp]!=0) temp = abs(rand())%40+1;
        role[temp]=2;
        if(i!=4) cout<<"("<<temp<<",3000),\n";
        else cout<<"("<<temp<<",3000);\n\n";
    }
   // for(int i=1;i<=40;i++) cout<<i<<" "<<role[i]<<endl;
    cout<<"====================\n";
    cout<<"Work\n\n";
    cout<<"INSERT INTO Work(SSN,ID_Building)\n";
    cout<<"VALUES ";
    int idx=1;
    for(int i=1;i<=40;i++){
        if(role[i]==1){
            //cout<<i<<endl;
            if(idx!=5){
                int t1,t2;
                t1=abs(rand()%5)+1;
                t2=abs(rand()%5)+1;
                used[idx][t1][t2]=2;
                stay[i].first=idx;
                stay[i].second=t1*10+t2;
                cout<<"("<<i<<","<<idx<<"),\n";
                manage[i].pb(idx++);
                cout<<"("<<i<<","<<idx<<"),\n";
                manage[i].pb(idx++);
            }
            else{
                cout<<"("<<i<<","<<idx<<"),\n";
                manage[i].pb(idx);
                int t1,t2;
                t1=abs(rand()%5)+1;
                t2=abs(rand()%5)+1;
                used[idx][t1][t2]=2;
                stay[i].first=idx;
                stay[i].second=t1*10+t2;
            }
        }
        else if(role[i]==2){
            int tmp=abs(rand())%5+1;
            bool work[6]={0};
            while(tmp--){
                int temp = rand()%5+1;
                while(work[temp]) temp = rand()%5+1;
                cout<<"("<<i<<","<<temp<<"),\n";
                work[temp]=1;
            }
        }
    }
    cout<<"====================\n";
    cout<<"Technician\n\n";
    cout<<"INSERT INTO Technician(SSN)\n";
    cout<<"VALUES ";
    for(int i=1;i<=40;i++){
        if(role[i]==2) cout<<"("<<i<<"),\n";
    }
    cout<<"====================\n";
    cout<<"Skill\n\n";
    cout<<"INSERT INTO Skill(Skill,TechnicianID)\n";
    cout<<"VALUES ";
    for(int i=1;i<=40;i++){
        if(role[i]==2){
            string skill[3]={"Electrical","Carpentry","Plumbing"};
            bool usedSkill[3]={0};
            for(int j=0;j<2;j++){
                int temp=abs(rand()%3);
                while(usedSkill[temp]) temp=abs(rand()%3);
                usedSkill[temp]=1;
                cout<<"('"<<skill[temp]<<"',"<<i<<"),\n";
            }
        }
    }
    cout<<"====================\n";
    cout<<"Manager\n\n";
    cout<<"INSERT INTO Manager(Manager_ID,ID_Apartment_Office,ID_Building_Office)\n";
    cout<<"VALUES ";
    for(int i=1;i<=40;i++){
        if(role[i]==1){
            cout<<"("<<i<<","<<stay[i].second<<","<<stay[i].first<<"),\n";
        }
    }
    cout<<"====================\n";
    cout<<"Tenant\n\n";
    cout<<"INSERT INTO Tenant(TenantID,ID_Apartment,ID_Building,BankAccount)\n";
    cout<<"VALUES ";
    for(int i=1;i<=40;i++){
        if(role[i]==0){
            idx=i;
            int t1,t2,t3;
            t1=abs(rand()%5)+1;
            t2=abs(rand()%5)+1;
            t3=abs(rand()%5)+1;
            while(used[t1][t2][t3]){
                t1=abs(rand()%5)+1;
                t2=abs(rand()%5)+1;
                t3=abs(rand()%5)+1;
            }
            used[t1][t2][t3]=1;
            for(int j=1;j<=bedroom[t1][t2][t3]&&idx<=40;j++){
                stay[idx].first=t1;
                stay[idx].second=t2*10+t3;
                string temp=generateBankAcc();
                cout<<"("<<idx<<","<<stay[idx].second<<","<<stay[idx].first<<",'"<<temp<<"'),\n";
                role[idx]=3;
                while(role[idx]!=0&&idx<=40) idx++;
            }
        }
    }
    cout<<"====================\n";
    cout<<"Next_of_kin\n\n";
    cout<<"INSERT INTO Next_of_kin(PhoneNumber,TenantID,Role)\n";
    cout<<"VALUES ";
    for(int i=1;i<=40;i++){
        if(role[i]==3){
            string r[4]={"Dad","Mom","Brother","Sister"};
            int temp=abs(rand()%4);
            cout<<"('"<<generatePhoneNumber()<<"',"<<i<<",'"<<r[temp]<<"'),\n";
        }
    }
    cout<<"====================\n";
    cout<<"Agreement\n\n";
    cout<<"INSERT INTO Agreement(ID_Agreement,ID_Apartment,ID_Building,Manager_ID,StartDate,EndDate,SecurityDeposit,MonthlyRent)\n";
    cout<<"VALUES ";
    idx=1;
    for(int i=1;i<=5;i++){
        for(int j=1;j<=5;j++){
            for(int k=1;k<=5;k++){
                if(used[i][j][k]==1){
                    idAgree[i][j][k]=idx++;
                    cout<<"("<<idAgree[i][j][k]<<","<<j<<k<<","<<i<<","<<findMana(i)<<",'2017-01-01','2020-01-01',1000,"<<bedroom[i][j][k]*100+bathroom[i][j][k]*50<<"),\n";
                }
            }
        }
    }
    cout<<"====================\n";
    cout<<"Signs\n\n";
    cout<<"INSERT INTO Signs(TenantID,ID_Agreement)\n";
    cout<<"VALUES ";
    for(int i=1;i<=40;i++){
        if(role[i]==3){
            cout<<"("<<i<<","<<idAgree[stay[i].first][stay[i].second/10][stay[i].second%10]<<"),\n";
        }
    }
    return 0;
}

using Student;
using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace Protobuf_Client
{
    class Client
    {

        Socket socketClient = null;
        public Client()
        {
            socketClient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            IPAddress serverIPAddress = IPAddress.Parse("192.168.218.1");
            int serverPort = 10003;
            IPEndPoint endpoint = new IPEndPoint(serverIPAddress, serverPort);
            socketClient.Connect(endpoint);
            SendStudent();

        }

        void SendStudent()
        {
            student_data stu = new student_data();
            stu.age = 20;
            stu.name = "阿斯顿";
            stu.sex = false;
            stu.contry = "中国";
            stu.grade = 1;

            try
            {

                //涉及格式转换，需要用到流，将二进制序列化到流中

                using (MemoryStream ms = new MemoryStream())
                {

                    //使用ProtoBuf工具的序列化方法

                    ProtoBuf.Serializer.Serialize<student_data>(ms, stu);

                    //定义二级制数组，保存序列化后的结果

                    byte[] result = new byte[ms.Length];

                    //将流的位置设为0，起始点

                    ms.Position = 0;

                    //将流中的内容读取到二进制数组中

                    ms.Read(result, 0, result.Length);
                    for (int i = 0; i < result.Length; i++)
                    {
                        Console.Write(result[i] + "\t");
                    }
                    byte[] length = BitConverter.GetBytes(result.Length);
                    Array.Reverse(length);
                    byte[] temp = new byte[result.Length + 4];
                    for (int i = 0; i < temp.Length; i++)
                    {
                        if (i < 4)
                        {
                            temp[i] = length[i];
                        }
                        else
                        {
                            temp[i] = result[i - 4];
                        }
                    }

                    socketClient.Send(temp);
                    Console.ReadLine();
                }

            }
            catch (Exception ex)
            {
            }


            //Student student = new Student("李磊", 20, false, "中国", 1);
            //byte[] buffer = Protobuffer(student);
            //socketClient.Send(buffer);
            //Console.ReadLine();


        }

        //byte[] Protobuffer(Student student)
        //{
        //    byte[] name = markByteArray(Encoding.UTF8.GetBytes(student.name), 1);

        //    byte[] age = markByteArray(checkByteLength(BitConverter.GetBytes(student.age)), 2);

        //    byte[] isGirl = markByteArray(new byte[] { student.isGirl == true ? (byte)1 : (byte)0 }, 3);

        //    byte[] contry = markByteArray(Encoding.UTF8.GetBytes(student.contry), 4);

        //    byte[] grade = markByteArray(checkByteLength(BitConverter.GetBytes(student.grade)), 5);


        //    byte[] result = new byte[4 + name.Length + age.Length + isGirl.Length + contry.Length + grade.Length];
        //    int index = 0;
        //    byte[] length = BitConverter.GetBytes(result.Length);
        //    for (int i = 0; i < 4; i++, index++)
        //    {
        //        result[index] = length[i];
        //    }
        //    for (int i = 0; i < name.Length; i++, index++)
        //    {
        //        result[index] = name[i];
        //    }
        //    for (int i = 0; i < age.Length; i++, index++)
        //    {
        //        result[index] = age[i];
        //    }
        //    for (int i = 0; i < isGirl.Length; i++, index++)
        //    {
        //        result[index] = isGirl[i];
        //    }          
        //    for (int i = 0; i < contry.Length; i++, index++)
        //    {
        //        result[index] = contry[i];
        //    }
        //    for (int i = 0; i < grade.Length; i++, index++)
        //    {
        //        result[index] = grade[i];
        //    }
        //    if (index == result.Length)
        //    {
        //        Console.WriteLine(result.Length);
        //    }

        //    return result;
        //}
        //public byte[] markByteArray(byte[] b, int index)//当b.length<32时可用
        //{
        //    int length = b.Length;
        //    byte mark = (byte)length;
        //    mark = (byte)(mark << 3);
        //    mark = (byte)(mark | index);
        //    byte[] by = new byte[length + 1];
        //    by[0] = mark;
        //    for (int i = 1; i < by.Length; i++)
        //    {
        //        by[i] = b[i - 1];
        //    }
        //    return by;
        //}
        //public byte[] checkByteLength(byte[] b)
        //{
        //    Array.Reverse(b);
        //    int index = 0;// [1][0][0][0]
        //    int length = b.Length;
        //    for (int i = 0; i < length; i++)
        //    {
        //        if (b[i] != 0)
        //        {
        //            index = i;
        //            break;
        //        }
        //    }

        //    if (index > 0)
        //    {
        //        byte[] temp = new byte[length - index];
        //        for (int i = 0; i < temp.Length; i++)
        //        {
        //            temp[i] = b[index];
        //            index++;
        //        }
        //        b = temp;
        //    }

        //    return b;

        //}
    }
    class Entrance
    {
        static void Main(string[] args)
        {


            Client client = new Client();

        }


    }
}

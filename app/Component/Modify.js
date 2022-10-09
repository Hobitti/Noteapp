
import React, {useState,useEffect} from 'react';
import CheckBox from '@react-native-community/checkbox';
import moment from 'moment';
import {StyleSheet, Text, View, TextInput, Button, TouchableOpacity } from 'react-native';

const App = () => {
  const [inputName, setNameNote,]=useState();
  const [inputText, setTextNote,]=useState();
  const [oldnote, setOldNote,]=useState({});
  const [isSelected, setToggleCheckBox] = useState(false)
  class note {
    constructor(title, content,date,notePublic,userID,noteID) {
      this.title = title;
      this.content = content;
      this.date = date;
      this.notePublic = notePublic;
      this.userID = userID
      this.noteID = noteID;
    }
}
  const newnote=new note("","","","","")
  
  
  const noteNameInputHandler=(input)=>{
    setNameNote(input)
  }

  const noteTextInputHandler=(input)=>{
    setTextNote(input)
  }

  const addFishToList=()=>{
    newnote.title=inputName;
    newnote.content=inputText
    newnote.notePublic=isSelected;
    newnote.date=moment().format("yyyy-MM-DD h:mm:ss");     
    newnote.userID=1;
    newnote.noteID=1;

  }

/*   @POST
	@Path("/addnote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addNote(Note n) {
		return Dao.addNewNote(n.getTitle(), n.getContent(), n.getDate(), n.isNotePublic(), n.getUserID());
	} */

  const getNote=async()=>{
    try{
      addFishToList();
      let response=await fetch("http://10.0.2.2:8080/rest/noteappservice/getnotedetails",
      {
        method:'POST',
        headers:{
          'Content-Type':'application/json'
        },
        body:JSON.stringify(newnote) 
      });
      let json=await response.json();
      setOldNote( JSON.parse(JSON.stringify(json.note)));
      setToggleCheckBox(oldnote.notePublic);
     // console.log(isSelected);
    }
    catch(error){
      console.log(error);
    }
  }
  

  const updateNote=async()=>{
    try{
      addFishToList();
      let response=await fetch("http://10.0.2.2:8080/rest/noteappservice/editnote",
      {
        method:'PUT',
        headers:{
          'Content-Type':'application/json'
        },
        body:JSON.stringify(newnote) 
      });
      let json=await response.text();
      console.log(json);
    }
    catch(error){
      console.log(error);
    }
  }
 
  useEffect(() => {
    getNote();
  }, []);

  return (
   
        <View style={styles.container}>
          <View style={styles.formStyle}>
            <TextInput style={styles.inputStyle} defaultValue={oldnote.title} onChangeText={(text)=>(noteNameInputHandler(text))} />
            <TextInput style={styles.inputStyleLong} defaultValue={oldnote.content} onChangeText={(text)=>(noteTextInputHandler(text))} multiline={true}/>
            <View style={styles.checkboxContainer}>
            <CheckBox
              disabled={false}
              value={isSelected}
              onValueChange={(newValue) => setToggleCheckBox(newValue)}
            />
            </View>
          </View>
          <View style={styles.formStyle}>
          <Button style={styles.buttonStyle} title='Edit Note' onPress={updateNote} />
          </View>
        </View>
        

  );
}


const styles = StyleSheet.create({
 screen: {
  padding:60,
 },
 container: {
  flex: 4,
  backgroundColor: '#fff',
  alignItems: 'center',
  justifyContent: 'center',
 },
 listItemStyle:{
  borderWidth:1,
  borderColor:"blue",
  padding:5,
  backgroundColor:"#abc",
  width:"100%",
},
 formStyle: {
  flexDirection: 'column',
  justifyContent:'space-between',
  alignitem:"center",
  
 },
checkboxContainer: {
  flexDirection: "row",
  marginBottom: 20,
},
checkbox: {
  alignSelf: "center",
},

 listStyle:{
  flex:8,
  alignItems:"center",
  backgroundColor:"#eac",
  borderColor:"green",
  borderWidth:2,
  width:"100%",
 },
 inputStyle:{
  backgroundColor:"#abc",
  borderColor:"black",
  borderWidth:2,
  margin:2,
  padding:5,
  width:150,
 },
 inputStyleLong:{
  backgroundColor:"#abc",
  borderColor:"black",
  borderWidth:2,
  margin:2,
  padding:5,  
  width:150,
  height:200,
  textAlignVertical: "top"
  
 },
 buttonStyle:{
  width:"20%",
  
 },
 buttonStyle2:{
  width:"20%",
  
 }
});

export default App;
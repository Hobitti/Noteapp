
import React, {useState} from 'react';
import CheckBox from '@react-native-community/checkbox';
import moment from 'moment';
import {StyleSheet, Text, View, TextInput, Button, TouchableOpacity } from 'react-native';

const AddNote = (userID) => {
  const [inputName, setNameNote,]=useState();
  const [inputText, setTextNote,]=useState();
  const [isSelected, setToggleCheckBox] = useState(false)
  class note {
    constructor(title, content,date,notePublic,userID) {
      this.title = title;
      this.content = content;
      this.date = date;
      this.notePublic = notePublic;
      this.userID = userID
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
    newnote.userID=userID;
    console.log(newnote);

  }
  const AppButton = ({onPress, title, type}) => (
    <TouchableOpacity
      onPress={onPress}
      style={
        type == 'main'
          ? styles.appButtonContainerMain
          : styles.appButtonContainerSecondary
      }>
      <Text
        style={
          type == 'main'
            ? styles.appButtonTextMain
            : styles.appButtonTextSecondary
        }>
        {title}
      </Text>
    </TouchableOpacity>
  );

/*   @POST
	@Path("/addnote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addNote(Note n) {
		return Dao.addNewNote(n.getTitle(), n.getContent(), n.getDate(), n.isNotePublic(), n.getUserID());
	} */

  const postNote=async()=>{
    try{
      addFishToList();
      let response=await fetch("http://10.0.2.2:8080/rest/noteappservice/addnote",
      {
        method:'POST',
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




  return (
        <View style={styles.mainContainer}>
          <View style={styles.contentContainer}>
            <View style={styles.form}>
              <View style={styles.formTitle}>
                <Text style={styles.header}>Add note</Text>
               </View>
              <TextInput style={styles.textInput} placeholder="Name" onChangeText={(text)=>(noteNameInputHandler(text))} />
              <TextInput style={styles.textInputLong} placeholder="Note" onChangeText={(text)=>(noteTextInputHandler(text))} multiline={true}/>
              <View style={styles.checkboxContainer}>
              <Text style={styles.inputTitle}>Public?
              <CheckBox
                disabled={false}
                value={isSelected}
                onValueChange={(newValue) => setToggleCheckBox(newValue)}
              /></Text>
              </View>
              <View style={styles.formButtons}>
                <View style={styles.formButton}> 
                  <AppButton   title='Add Note'  type="main" onPress={postNote} />
                </View>
              </View>
            </View>
          </View>
        </View>
        

  );
}


const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'rgba(248, 52, 108, 0.7)',
  },
  contentContainer: {
    flex: 1,
    backgroundColor: '#fff',
    top: 110,
    borderTopLeftRadius: 40,
    borderTopRightRadius: 40,
    alignItems: 'center',
  },
  logo: {
    bottom: 66.5,
  },
  form: {
    flex: 1,
    width: 220,
  },
  formTitle: {
    alignItems: 'center',
    bottom: 30,
  },
  header: {
    fontFamily: 'RobotoCondensed-Bold',
    fontSize: 30,
    color: '#000',
  },
  formInput: {
    marginBottom: 15,
  },
  inputTitle: {
    fontFamily: 'RobotoCondensed-Regular',
    color: '#000',
    marginLeft: 10,
    fontSize: 16,
  },
  textInput: {
    borderColor: 'rgba(49, 198, 232, 0.5)',
    borderWidth: 2,
    borderRadius: 15,
    paddingLeft: 10,
    fontFamily: 'RobotoCondensed-Regular',
    fontSize: 14,
    marginBottom: 15,
  },
  textInputLong: {
    borderColor: 'rgba(49, 198, 232, 0.5)',
    borderWidth: 2,
    borderRadius: 15,
    paddingLeft: 10,
    fontFamily: 'RobotoCondensed-Regular',
    fontSize: 14,
    height: 150,
    marginBottom: 15,
  },
  formButtons: {
    marginTop: 20,
  },
  formButton: {
    marginBottom: 15,
  },
  appButtonContainerMain: {
    elevation: 6,
    backgroundColor: '#31E89F',
    borderRadius: 15,
    paddingVertical: 12,
    paddingHorizontal: 12,
  },
  appButtonTextMain: {
    fontSize: 16,
    color: '#fff',
    fontFamily: 'RobotoCondensed-Bold',
    alignSelf: 'center',
    textTransform: 'uppercase',
  },
  appButtonContainerSecondary: {
    elevation: 6,
    backgroundColor: '#fff',
    borderColor: '#31E89F',
    borderWidth: 2,
    borderRadius: 15,
    paddingVertical: 12,
    paddingHorizontal: 12,
  },
  appButtonTextSecondary: {
    fontSize: 16,
    color: '#000',
    fontFamily: 'RobotoCondensed-Bold',
    alignSelf: 'center',
    textTransform: 'uppercase'
  },
  passwordInput: {
    position: 'relative'
  },
  icon: {
    fontSize: 18
  },
  iconInInput: {
    position: 'absolute',
    top: 0,
    right: 8,
    bottom: 0,
    justifyContent: 'center',
  },
});

export default App;
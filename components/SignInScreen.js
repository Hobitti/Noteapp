import React, {useState} from 'react';
import {Text, View, TextInput, StyleSheet, Image} from 'react-native';
import CustomButton from './CustomButton';
import Icon from 'react-native-vector-icons/FontAwesome5';

const SignInScreen = (props) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passHidden, setPassHidden] = useState(true);

  const usernameHandler = text => {
    setUsername(text);
  };

  const passwordHandler = text => {
    setPassword(text);
  };

  const checkLogin = async () => {
    try {
      let response = await fetch(
        'http://10.0.2.2:8080/rest/noteappservice/checkuserlogin',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({username: username, password: password}),
        },
      );

      let json = await response.json();
      console.log(json);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <View style={styles.mainContainer}>
      <View style={styles.contentContainer}>
        <View style={styles.logo}>
          <Image source={require('../assets/images/logo.png')} />
        </View>

        <View style={styles.form}>
          <View style={styles.formTitle}>
            <Text style={styles.header}>Sign In</Text>
          </View>

          <View style={styles.formInput}>
            <Text style={styles.inputTitle}>Username</Text>
            <TextInput
              style={styles.textInput}
              placeholder="Enter your username"
              onChangeText={usernameHandler}
            />
          </View>

          <View style={styles.formInput}>
            <Text style={styles.inputTitle}>Password</Text>
            <View style={styles.passwordInput}>
              <TextInput
                style={styles.textInput}
                placeholder="Enter your password"
                onChangeText={passwordHandler}
                secureTextEntry={passHidden}
              />
              <View style={styles.iconInInput}>
                <Icon
                  onPress={() => setPassHidden(!passHidden)}
                  name={passHidden ? 'eye' : 'eye-slash'}
                  style={styles.icon}
                  color={
                    passHidden
                      ? 'rgba(49, 198, 232, 1)'
                      : 'rgba(49, 198, 232, 1)'
                  }
                />
              </View>
            </View>
          </View>

          <View style={styles.formButtons}>
            <View style={styles.formButton}>
              <CustomButton title="login" type="main" onPress={checkLogin} />
            </View>
            <View style={styles.formButton}>
              <CustomButton
                title="sign up"
                type="secondary"
                onPress={() => props.navigation.navigate('SignUpScreen')}
              />
            </View>
          </View>
        </View>
      </View>
    </View>
  );
};

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
  },
  formButtons: {
    marginTop: 20,
  },
  formButton: {
    marginBottom: 15,
  },
  passwordInput: {
    position: 'relative',
  },
  icon: {
    fontSize: 18,
  },
  iconInInput: {
    position: 'absolute',
    top: 0,
    right: 8,
    bottom: 0,
    justifyContent: 'center',
  },
});

export default SignInScreen;
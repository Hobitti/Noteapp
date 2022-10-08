import React, {useState, useEffect, createRef} from 'react';
import {Text, View, StyleSheet, Image} from 'react-native';

import Icon from 'react-native-vector-icons/FontAwesome5';
import Toast from 'react-native-toast-message';

import CustomButton from './CustomButton';
import CustomTextInput from './CustomTextInput';

const SignInScreen = props => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passHidden, setPassHidden] = useState(true);
  const [nameError, setNameError] = useState('');
  const [passError, setPassError] = useState('');

  const clearInputsAndErrors = () => {
    setUsername('');
    setPassword('');
    setNameError('');
    setPassError('');
  }

  useEffect(() => {
    if (props.route.params?.username) {
      setUsername(props.route.params.username);
    }
  }, [props.route.params?.username]);

  let passwordInputRef = createRef();
  
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
      if (json.userID == 0 || json.userID == null) {
        setNameError('Incorrect credentials');
        setPassError('Incorrect credentials');
        Toast.show({
          type: 'error',
          text1: 'Incorrect login credentials',
          text2: 'Check your login details and try again',
        });
      } else {
        setNameError('');
        setPassError('');
        Toast.show({
          type: 'success',
          text1: 'Login succesful',
          text2: 'Welcome ' + json.username + '!',
        });
      }
    } catch (error) {
      console.log(error);
      Toast.show({
        type: 'error',
        text1: 'Login failed',
        text2: 'Server error. Try again later',
      });
    }
  };

  return (
    <View style={styles.componentContainer}>
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
            <CustomTextInput
              value={username}
              placeholder="Enter your username"
              onChangeText={text => {
                setUsername(text);
              }}
              maxLength={30}
              error={nameError}
              onSubmitEditing={() => passwordInputRef.current.focus()}
              blurOnSubmit={false}
            />
            {!!nameError && <Text style={styles.error}>{nameError}</Text>}
          </View>

          <View style={styles.formInput}>
            <Text style={styles.inputTitle}>Password</Text>
            <View style={styles.passwordInput}>

              <View 
                            onTouchEnd={(e) => e.stopPropagation()}>
                <CustomTextInput
                  value={password}
                  placeholder="Enter your username"
                  secureTextEntry={passHidden}
                  onChangeText={text => {
                    setPassword(text);
                  }}
                  ref={passwordInputRef}
                  maxLength={40}
                  error={passError}
                />
              </View>
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
            {!!passError && <Text style={styles.error}>{passError}</Text>}
          </View>

          <View style={styles.formButtons}>
            <View style={styles.formButton}>
              <CustomButton title="login" type="main" onPress={checkLogin} />
            </View>
            <View style={styles.formButton}>
              <CustomButton
                title="sign up"
                type="secondary"
                onPress={() => {clearInputsAndErrors(); props.navigation.navigate('SignUpScreen')}}
              />
            </View>
          </View>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  componentContainer: {
    flex: 1,
    backgroundColor: 'rgba(248, 52, 108, 0.7)',
  },
  contentContainer: {
    flex: 1,
    backgroundColor: '#fff',
    top: 80,
    borderTopLeftRadius: 40,
    borderTopRightRadius: 40,
    alignItems: 'center',
  },
  logo: {
    bottom: 66.5,
  },
  form: {
    flex: 1,
    width: 230,
    bottom: 20
  },
  formTitle: {
    alignItems: 'center',
    bottom: 25,
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
    marginBottom: 2,
    fontSize: 16,
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
  error: {
    fontSize: 14,
    color: '#F8346C',
    marginLeft: 10,
  },
});

export default SignInScreen;

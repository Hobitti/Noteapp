import React, {useState} from 'react';
import {TextInput, StyleSheet} from 'react-native';

const CustomTextInput = props => {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <TextInput
      {...props}
      style={[
        styles.textInput,
        props.style,
        isFocused && {borderColor: 'rgba(49, 198, 232, 1)', borderRadius: 5},
        props.error && {borderColor: '#F8346C'}
      ]}
      onBlur={() => setIsFocused(false)}
      onFocus={() => setIsFocused(true)}
    />
  );
};

const styles = StyleSheet.create({
  textInput: {
    borderColor: 'rgba(49, 198, 232, 0.5)',
    borderWidth: 2,
    borderRadius: 15,
    paddingLeft: 10,
    fontFamily: 'RobotoCondensed-Regular',
    fontSize: 14,
  },
});

export default CustomTextInput;

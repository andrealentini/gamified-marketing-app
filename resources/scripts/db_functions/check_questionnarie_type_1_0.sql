CREATE DEFINER=`root`@`localhost` FUNCTION `check_questionnaire_type_1_0`(marketing_key INT, statistical_key INT) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
RETURN (SELECT questionnaire.type FROM questionnaire WHERE marketing_key=id)=0 AND (SELECT questionnaire.type FROM questionnaire WHERE statistical_key=id)=1;
END
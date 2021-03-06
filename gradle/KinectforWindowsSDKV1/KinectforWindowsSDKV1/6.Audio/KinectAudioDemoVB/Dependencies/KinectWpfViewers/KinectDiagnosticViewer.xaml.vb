Imports Microsoft.Kinect
Imports System.IO

'------------------------------------------------------------------------------
' <copyright file="KinectDiagnosticViewer.xaml.cs" company="Microsoft">
'     Copyright (c) Microsoft Corporation.  All rights reserved.
' </copyright>
'------------------------------------------------------------------------------

Namespace Microsoft.Samples.Kinect.WpfViewers

	''' <summary>
	''' Interaction logic for KinectDiagnosticViewer.xaml
	''' </summary>
	Partial Public Class KinectDiagnosticViewer
		Inherits UserControl
		Private ReadOnly kinectSettings As KinectSettings
		Private ReadOnly sensorIsInitialized As New Dictionary(Of KinectSensor, Boolean)()
'INSTANT VB NOTE: The variable kinect was renamed since Visual Basic does not allow class members with the same name:
		Private kinect_Renamed As KinectSensor
		Private kinectAppConflict As Boolean

		Public Sub New()
			InitializeComponent()
			Me.kinectSettings = New KinectSettings(Me)
			Me.kinectSettings.PopulateComboBoxesWithFormatChoices()
			Settings.Content = Me.kinectSettings
			Me.KinectColorViewer = Me.colorViewer
			Me.StatusChanged()
		End Sub

		Public Property KinectColorViewer() As ImageViewer

		Public Property Kinect() As KinectSensor
			Get
				Return Me.kinect_Renamed
			End Get

			Set(ByVal value As KinectSensor)
				If Me.kinect_Renamed IsNot Nothing Then
					Dim wasInitialized As Boolean
					Me.sensorIsInitialized.TryGetValue(Me.kinect_Renamed, wasInitialized)
					If wasInitialized Then
						Me.UninitializeKinectServices(Me.kinect_Renamed)
						Me.sensorIsInitialized(Me.kinect_Renamed) = False
					End If
				End If

				Me.kinect_Renamed = value
				Me.kinectSettings.Kinect = value
				If Me.kinect_Renamed IsNot Nothing Then
					If Me.kinect_Renamed.Status = KinectStatus.Connected Then
						Me.kinect_Renamed = Me.InitializeKinectServices(Me.kinect_Renamed)

						If Me.kinect_Renamed IsNot Nothing Then
							Me.sensorIsInitialized(Me.kinect_Renamed) = True
						End If
					End If
				End If

				Me.StatusChanged() ' update the UI about this sensor
			End Set
		End Property

		Public Sub StatusChanged()
			If Me.kinectAppConflict Then
				status.Text = "KinectAppConflict"
			ElseIf Me.Kinect Is Nothing Then
				status.Text = "Kinect initialize failed"
			Else
				Me.status.Text = Me.Kinect.Status.ToString()

				If Me.Kinect.Status = KinectStatus.Connected Then
					' Update comboboxes' selected value based on stream isenabled/format.
					Me.kinectSettings.colorFormats.SelectedValue = Me.Kinect.ColorStream.Format
					Me.kinectSettings.depthFormats.SelectedValue = Me.Kinect.DepthStream.Format
					Me.kinectSettings.trackingModes.SelectedValue = KinectSkeletonViewerOnDepth.TrackingMode

					Me.kinectSettings.UpdateUiElevationAngleFromSensor()
				End If
			End If
		End Sub

		' Kinect enabled apps should customize which Kinect services it initializes here.
		Private Function InitializeKinectServices(ByVal sensor As KinectSensor) As KinectSensor
			' Centralized control of the formats for Color/Depth and enabling skeletalViewer
			sensor.ColorStream.Enable(ColorImageFormat.RgbResolution640x480Fps30)
			sensor.DepthStream.Enable(DepthImageFormat.Resolution320x240Fps30)
			Me.kinectSettings.SkeletonStreamEnable.IsChecked = True ' will enable SkeletonStream if available

			' Inform the viewers of the Kinect KinectSensor.
			Me.KinectColorViewer.Kinect = sensor
			KinectDepthViewer.Kinect = sensor
			KinectSkeletonViewerOnColor.Kinect = sensor
			KinectSkeletonViewerOnDepth.Kinect = sensor
			kinectAudioViewer.Kinect = sensor

			' Start streaming
			Try
				sensor.Start()
				Me.kinectAppConflict = False
			Catch e1 As IOException
				Me.kinectAppConflict = True
				Return Nothing
			End Try

			sensor.AudioSource.Start()
			Return sensor
		End Function

		' Kinect enabled apps should uninitialize all Kinect services that were initialized in InitializeKinectServices() here.
		Private Sub UninitializeKinectServices(ByVal sensor As KinectSensor)
			sensor.AudioSource.Stop()

			' Stop streaming
			sensor.Stop()

			' Inform the viewers that they no longer have a Kinect KinectSensor.
			Me.KinectColorViewer.Kinect = Nothing
			KinectDepthViewer.Kinect = Nothing
			KinectSkeletonViewerOnColor.Kinect = Nothing
			KinectSkeletonViewerOnDepth.Kinect = Nothing
			kinectAudioViewer.Kinect = Nothing

			' Disable skeletonengine, as only one Kinect can have it enabled at a time.
			If sensor.SkeletonStream IsNot Nothing Then
				sensor.SkeletonStream.Disable()
			End If
		End Sub
	End Class
End Namespace
